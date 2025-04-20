package org.msa.one.notification.listener;

import lombok.Data;

@Data
public class BookingEvent {
    private String bookingId;
    private String userId;
    private String type;
    private String channel;
}