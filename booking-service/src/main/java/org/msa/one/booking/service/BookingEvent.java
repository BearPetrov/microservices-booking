package org.msa.one.booking.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {
    private UUID bookingId;
    private UUID userId;
    private String type;
    private String channel;
}
