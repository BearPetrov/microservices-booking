package org.msa.one.booking.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange promoExchange() {
        return new TopicExchange("promo-exchange");
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange("notification-exchange");
    }

    @Bean
    public Queue promoQueue() {
        return new Queue("promo-queue");
    }

    @Bean
    public Queue bookingCreatedQueue() {
        return new Queue("booking-created-queue");
    }

    @Bean
    public Queue bookingCanceledQueue() {
        return new Queue("booking-canceled-queue");
    }

    @Bean
    public Binding promoBinding(Queue promoQueue, TopicExchange promoExchange) {
        return BindingBuilder.bind(promoQueue).to(promoExchange).with("promo.events");
    }

    @Bean
    public Binding bookingCreatedBinding(Queue bookingCreatedQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(bookingCreatedQueue).to(notificationExchange).with("booking.created");
    }

    @Bean
    public Binding bookingCanceledBinding(Queue bookingCanceledQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(bookingCanceledQueue).to(notificationExchange).with("booking.canceled");
    }
}
