package org.msa.one.booking.service;

import org.msa.one.booking.api.dto.BookingRequest;
import org.msa.one.booking.api.dto.BookingResponse;
import org.msa.one.booking.entity.Booking;
import org.msa.one.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final SeatAvailabilityClient seatAvailabilityClient;
    private final MovieCatalogClient movieCatalogClient;
    private final NotificationClient notificationClient;
    private final PromoClient promoClient;

    public BookingService(BookingRepository bookingRepository,
                          SeatAvailabilityClient seatAvailabilityClient,
                          MovieCatalogClient movieCatalogClient,
                          NotificationClient notificationClient,
                          PromoClient promoClient) {
        this.bookingRepository = bookingRepository;
        this.seatAvailabilityClient = seatAvailabilityClient;
        this.movieCatalogClient = movieCatalogClient;
        this.notificationClient = notificationClient;
        this.promoClient = promoClient;
    }

    public BookingResponse createBooking(BookingRequest request) {
        log.info("Creating booking for userEmail={} movieId={} seats={}",
                request.getUserEmail(), request.getMovieId(), request.getSeats());

        boolean isAvailable = seatAvailabilityClient.checkAvailability(request.getMovieId(), request.getSeats());
        if (!isAvailable) {
            throw new IllegalStateException("Requested seats are not available for movieId=" + request.getMovieId());
        }

        movieCatalogClient.validateShowtime(request.getMovieId(), request.getShowTime());

        Booking booking = new Booking();
        booking.setUserEmail(request.getUserEmail());
        booking.setMovieId(request.getMovieId());
        booking.setShowTime(request.getShowTime());
        booking.setSeats(request.getSeats());
        booking.setBookingCode(generateBookingCode());
        booking.setCreatedAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);
        log.info("Booking with ID={} created successfully", saved.getId());

        notificationClient.sendBookingCreatedEvent(saved);
        seatAvailabilityClient.reserveSeats(saved.getMovieId(), saved.getSeats());

        return convertToResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<BookingResponse> getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByEmail(String email) {
        return bookingRepository.findByUserEmail(email)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id=" + bookingId));

        if (booking.getCanceledAt() != null) {
            log.warn("Booking with ID={} is already canceled.", bookingId);
            return;
        }

        booking.setCanceledAt(LocalDateTime.now());
        bookingRepository.save(booking);

        seatAvailabilityClient.releaseSeats(booking.getMovieId(), booking.getSeats());
        notificationClient.sendBookingCanceledEvent(booking);
        promoClient.sendPromoEvent(booking.getUserEmail());

        log.info("Booking with ID={} canceled", bookingId);
    }

    private BookingResponse convertToResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userEmail(booking.getUserEmail())
                .movieId(booking.getMovieId())
                .showTime(booking.getShowTime())
                .seats(booking.getSeats())
                .bookingCode(booking.getBookingCode())
                .createdAt(booking.getCreatedAt())
                .canceledAt(booking.getCanceledAt())
                .build();
    }

    private String generateBookingCode() {
        return "BOOK-" + System.currentTimeMillis();
    }
}