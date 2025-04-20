package org.msa.one.booking.service;

import org.msa.one.booking.api.dto.BookingRequest;
import org.msa.one.booking.api.dto.BookingResponse;
import org.msa.one.booking.entity.Booking;
import org.msa.one.booking.entity.BookingHistory;
import org.msa.one.booking.entity.BookingSeat;
import org.msa.one.booking.repository.BookingHistoryRepository;
import org.msa.one.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final BookingHistoryRepository bookingHistoryRepository;

    public BookingService(BookingRepository bookingRepository,
                          SeatAvailabilityClient seatAvailabilityClient,
                          MovieCatalogClient movieCatalogClient,
                          NotificationClient notificationClient,
                          PromoClient promoClient,
                          BookingHistoryRepository bookingHistoryRepository) {
        this.bookingRepository = bookingRepository;
        this.seatAvailabilityClient = seatAvailabilityClient;
        this.movieCatalogClient = movieCatalogClient;
        this.notificationClient = notificationClient;
        this.promoClient = promoClient;
        this.bookingHistoryRepository = bookingHistoryRepository;
    }

    private BigDecimal calculateTotalPrice(List<String> seats, BigDecimal pricePerSeat) {
        return pricePerSeat.multiply(BigDecimal.valueOf(seats.size()));
    }

    public BookingResponse createBooking(BookingRequest request) {
        log.info("Creating booking for userEmail={} movieId={} seats={}",
                request.getUserEmail(), request.getMovieId(), request.getSeats());

        boolean isAvailable = seatAvailabilityClient.checkAvailability(request.getMovieId(), request.getSeats());
        if (!isAvailable) {
            throw new IllegalStateException("Requested seats are not available for movieId=" + request.getMovieId());
        }

        movieCatalogClient.validateShowtime(request.getMovieId(), request.getShowTime());

        if (request.getShowtimeId() == null) {
            throw new IllegalArgumentException("ShowtimeId cannot be null");
        }

        BigDecimal pricePerSeat = new BigDecimal("10.00");
        List<String> seatList = List.of(request.getSeats().split(","));
        BigDecimal totalPrice = calculateTotalPrice(seatList, pricePerSeat);

        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .showtimeId(request.getShowtimeId())
                .userEmail(request.getUserEmail())
                .movieId(request.getMovieId())
                .showTime(request.getShowTime())
                .seats(String.join(",", seatList))
                .bookingCode(generateBookingCode())
                .createdAt(LocalDateTime.now())
                .status("ACTIVE")
                .totalPrice(totalPrice)
                .build();

        List<BookingSeat> bookingSeats = seatList.stream()
                .map(seat -> BookingSeat.builder()
                        .seatLabel(seat)
                        .price(pricePerSeat)
                        .booking(booking)
                        .build())
                .toList();

        bookingSeats.forEach(seat -> seat.setBooking(booking));

        Booking saved = bookingRepository.save(booking);
        log.info("Booking with ID={} created successfully", saved.getId());

        saveBookingHistory(saved.getId(), "CREATED");
        notificationClient.sendBookingCreatedEvent(saved);
        seatAvailabilityClient.reserveSeats(saved.getMovieId(), saved.getSeats());

        return convertToResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<BookingResponse> getBooking(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByEmail(String email) {
        return bookingRepository.findAll().stream()
                .filter(booking -> email.equals(booking.getUserEmail()))
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional
    public void cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id=" + bookingId));

        if (booking.getCanceledAt() != null) {
            log.warn("Booking with ID={} is already canceled.", bookingId);
            return;
        }

        booking.setCanceledAt(LocalDateTime.now());
        bookingRepository.save(booking);

        saveBookingHistory(booking.getId(), "CANCELED");

        if (booking.getSeats() == null || booking.getSeats().isEmpty()) {
            log.warn("No seats found for booking ID={}", bookingId);
            return;
        }

        seatAvailabilityClient.releaseSeats(booking.getMovieId(), booking.getSeats());
        notificationClient.sendBookingCanceledEvent(booking);
        promoClient.sendPromoEvent(booking.getUserEmail());

        log.info("Booking with ID={} canceled", bookingId);
    }

    private BookingResponse convertToResponse(Booking booking) {
        List<String> seatList = List.of(booking.getSeats().split(","));
        return BookingResponse.builder()
                .id(booking.getId().toString())
                .userEmail(booking.getUserEmail())
                .movieId(booking.getMovieId())
                .showTime(booking.getShowTime())
                .seats(String.join(",", seatList))
                .bookingCode(booking.getBookingCode())
                .createdAt(booking.getCreatedAt())
                .canceledAt(booking.getCanceledAt())
                .build();
    }

    private String generateBookingCode() {
        return "BOOK-" + System.currentTimeMillis();
    }

    private void saveBookingHistory(UUID bookingId, String changeType) {
        BookingHistory history = BookingHistory.builder()
                .bookingId(bookingId)
                .changeType(changeType)
                .timestamp(LocalDateTime.now())
                .build();
        bookingHistoryRepository.save(history);
    }
}