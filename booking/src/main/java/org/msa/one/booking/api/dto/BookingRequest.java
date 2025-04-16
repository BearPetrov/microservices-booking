package org.msa.one.booking.api.dto;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запит на створення бронювання.
 */
@Data
public class BookingRequest {
    private String userEmail;
    private Long movieId;
    private LocalDateTime showTime;
    private String seats;

    public String getUserEmail() {
        return userEmail;
    }
    public Long getMovieId() {
        return movieId;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public String getSeats() {
        return seats;
    }
}