package org.msa.one.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;

    @Column(name = "change_type", nullable = false)
    private String changeType; // Например: "CREATED", "UPDATED", "CANCELED"

    @Column(nullable = false)
    private LocalDateTime timestamp;
}