package org.msa.one.notification.service;

import lombok.RequiredArgsConstructor;
import org.msa.one.notification.entity.SentNotification;
import org.msa.one.notification.repository.SentNotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SentNotificationRepository sentNotificationRepository;

    public void handleBookedEvent(String bookingId, String userId, String type, String channel) {
        SentNotification notification = SentNotification.builder()
                .bookingId(bookingId)
                .userId(userId)
                .messageSubject("Booking Confirmed")
                .messageBody("Your booking has been confirmed.")
                .type(type)
                .channel(channel)
                .sentAt(LocalDateTime.now())
                .status("SENT")
                .build();

        sentNotificationRepository.save(notification);
    }
}