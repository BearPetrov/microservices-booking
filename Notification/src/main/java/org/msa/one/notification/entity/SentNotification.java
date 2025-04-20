package org.msa.one.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sent_notifications")
public class SentNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id")
    private String bookingId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "message_subject", nullable = false)
    private String messageSubject;

    @Column(name = "message_body", columnDefinition = "TEXT", nullable = false)
    private String messageBody;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}