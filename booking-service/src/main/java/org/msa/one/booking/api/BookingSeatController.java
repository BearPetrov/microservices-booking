package org.msa.one.booking.api;

import lombok.RequiredArgsConstructor;
import org.msa.one.booking.entity.Booking;
import org.msa.one.booking.entity.BookingSeat;
import org.msa.one.booking.repository.BookingRepository;
import org.msa.one.booking.repository.BookingSeatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking_seats")
@RequiredArgsConstructor
public class BookingSeatController {

    private final BookingSeatRepository bookingSeatRepository;
    private final BookingRepository bookingRepository;

    @GetMapping
    public List<BookingSeat> getAllBookingSeats() {
        return bookingSeatRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createBookingSeat(@RequestBody BookingSeat bookingSeat) {
        try {
            if (bookingSeat.getBooking() == null || bookingSeat.getBooking().getId() == null) {
                throw new IllegalArgumentException("Booking ID must not be null");
            }

            Booking booking = bookingRepository.findById(bookingSeat.getBooking().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID=" + bookingSeat.getBooking().getId()));

            bookingSeat.setBooking(booking);
            bookingSeatRepository.save(bookingSeat);

            return ResponseEntity.ok("BookingSeat created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/by-booking/{bookingId}")
    public List<BookingSeat> getSeatsByBookingId(@PathVariable String bookingId) {
        return bookingSeatRepository.findAll().stream()
                .filter(seat -> seat.getBooking().getId().toString().equals(bookingId))
                .toList();
    }
}