package org.msa.one.booking.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private String userEmail;
    private Long movieId;
    private LocalDateTime showTime;
    private String seats;
    private String bookingCode;
    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;

    public static BookingResponseBuilder builder() {
        return new BookingResponseBuilder();
    }

    public static class BookingResponseBuilder {
        private final BookingResponse response = new BookingResponse();

        public BookingResponseBuilder id(Long id) {
            response.id = id;
            return this;
        }

        public BookingResponseBuilder userEmail(String userEmail) {
            response.userEmail = userEmail;
            return this;
        }

        public BookingResponseBuilder movieId(Long movieId) {
            response.movieId = movieId;
            return this;
        }

        public BookingResponseBuilder showTime(LocalDateTime showTime) {
            response.showTime = showTime;
            return this;
        }

        public BookingResponseBuilder seats(String seats) {
            response.seats = seats;
            return this;
        }

        public BookingResponseBuilder bookingCode(String bookingCode) {
            response.bookingCode = bookingCode;
            return this;
        }

        public BookingResponseBuilder createdAt(LocalDateTime createdAt) {
            response.createdAt = createdAt;
            return this;
        }

        public BookingResponseBuilder canceledAt(LocalDateTime canceledAt) {
            response.canceledAt = canceledAt;
            return this;
        }

        public BookingResponse build() {
            return response;
        }
    }
}
