package org.msa.one.booking.service;


import org.msa.one.booking.entity.Booking;
import org.msa.one.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    // Конструктор для ін'єкції репозиторію
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking) {
        // Логіка: згенерувати bookingCode, перевірити вільні місця тощо
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBooking(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByUserEmail(email);
    }

    public void cancelBooking(Long id) {
        // Логіка скасування — видалити бронювання і звільнити місця
        bookingRepository.deleteById(id);
    }
}