package org.msa.one.booking.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private String userEmail;
    private Long movieId;
    private LocalDateTime showTime;
    private String seats;
}