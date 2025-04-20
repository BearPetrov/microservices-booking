package org.msa.one.booking.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {
    private String bookingId;
    private String userId;
    private String type;
    private String channel;
}