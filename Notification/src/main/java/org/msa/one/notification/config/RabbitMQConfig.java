package org.msa.one.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue bookingNotificationsQueue() {
        return new Queue("booking.notifications.queue", true);
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange("notification-exchange");
    }

    @Bean
    public Binding bookingNotificationsBinding(Queue bookingNotificationsQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(bookingNotificationsQueue).to(notificationExchange).with("booking.notifications.routingKey");
    }
}