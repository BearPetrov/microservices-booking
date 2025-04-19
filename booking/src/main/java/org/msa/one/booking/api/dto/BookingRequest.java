package org.msa.one.booking.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingRequest {
    private String userEmail;
    private Long movieId;
    private LocalDateTime showTime;
    private String seats;
    private UUID userId;
    private UUID showtimeId;
}