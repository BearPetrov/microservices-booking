package org.msa.one.notification.listener;

import lombok.RequiredArgsConstructor;
import org.msa.one.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "booking.notifications.queue")
    public void handleBookingEvent(BookingEvent event) {
        notificationService.handleBookedEvent(
                event.getBookingId(),
                event.getUserId(),
                event.getType(),
                event.getChannel()
        );
    }
}