package org.msa.one.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private UUID bookingId;
    private UUID userId;
    private String type;
    private String channel;
} 