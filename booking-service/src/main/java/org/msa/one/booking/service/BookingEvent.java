package org.msa.one.booking.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent implements Serializable {
    private String bookingId;
    private String userId;
    private String type;
    private String channel;
}