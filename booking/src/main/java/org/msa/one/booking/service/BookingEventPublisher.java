package org.msa.one.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishBookingEvent(String bookingId, String userId, String type, String channel) {
        BookingEvent event = new BookingEvent(bookingId, userId, type, channel);
        rabbitTemplate.convertAndSend("notification-exchange", "booking.notifications.routingKey", event);
    }
}