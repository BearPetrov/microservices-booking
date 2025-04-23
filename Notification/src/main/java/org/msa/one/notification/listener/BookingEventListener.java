package org.msa.one.notification.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.one.event.BookingEvent;
import org.msa.one.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


    @Component
    @RequiredArgsConstructor
    public class BookingEventListener {

        private final NotificationService notificationService;

        @RabbitListener(queues = "booking-created-queue", containerFactory = "rabbitListenerContainerFactory")
        public void handleBookingCreatedEvent(BookingEvent event) {
            System.out.println("📥 [Listener] Got booking: " + event);
            notificationService.handleBookedEvent(
                    event.getBookingId(),
                    event.getUserId(),
                    event.getType(),
                    event.getChannel()
            );
        }
    }