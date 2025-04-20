package org.msa.one.booking.api;

import lombok.RequiredArgsConstructor;
import org.msa.one.booking.entity.BookingHistory;
import org.msa.one.booking.repository.BookingHistoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking_history")
@RequiredArgsConstructor
public class BookingHistoryController {

    private final BookingHistoryRepository bookingHistoryRepository;

    @GetMapping("/{bookingId}")
    public List<BookingHistory> getHistoryByBookingId(@PathVariable UUID bookingId) {
        return bookingHistoryRepository.findAll().stream()
                .filter(history -> history.getBookingId().equals(bookingId))
                .toList();
    }
}