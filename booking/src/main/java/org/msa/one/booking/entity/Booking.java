package org.msa.one.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private LocalDateTime showTime;
    private Long movieId;
    private String bookingCode;
    private String seats;

    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
    private String status; // Например: "ACTIVE" или "CANCELED"
}
