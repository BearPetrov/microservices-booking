package org.msa.one.booking.service;

import org.msa.one.booking.entity.Booking;
import org.msa.one.booking.service.BookingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationClient {
    private static final Logger log = LoggerFactory.getLogger(NotificationClient.class);

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NOTIFICATION = "notification-exchange";
    private static final String ROUTING_KEY_BOOKING_CREATED = "booking.created";
    private static final String ROUTING_KEY_BOOKING_CANCELED = "booking.canceled";

    public NotificationClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBookingCreatedEvent(Booking booking) {
        log.info("📤 Sending booking-created event, bookingId={}", booking.getId());

        BookingEvent event = new BookingEvent(
                booking.getId().toString(),
                booking.getUserId().toString(),
                "CREATED",
                "EMAIL"
        );

        rabbitTemplate.convertAndSend(EXCHANGE_NOTIFICATION, ROUTING_KEY_BOOKING_CREATED, event);
    }

    public void sendBookingCanceledEvent(Booking booking) {
        log.info("📤 Sending booking-canceled event, bookingId={}", booking.getId());

        BookingEvent event = new BookingEvent(
                booking.getId().toString(),
                booking.getUserId().toString(),
                "CANCELED",
                "EMAIL"
        );

        rabbitTemplate.convertAndSend(EXCHANGE_NOTIFICATION, ROUTING_KEY_BOOKING_CANCELED, event);
    }
}
