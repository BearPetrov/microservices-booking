package org.msa.one.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "showtime_id", nullable = false)
    private UUID showtimeId;

    @Column(nullable = false)
    private String status;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "seats", nullable = false)
    private String seats;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "show_time")
    private LocalDateTime showTime;

    @Column(name = "booking_code")
    private String bookingCode;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }


}