package org.msa.one.booking.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private String id; // Изменено с Long на String
    private String userEmail;
    private Long movieId;
    private LocalDateTime showTime;
    private String seats;
    private String bookingCode;
    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
}