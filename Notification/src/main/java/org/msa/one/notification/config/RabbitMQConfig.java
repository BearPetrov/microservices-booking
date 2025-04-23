package org.msa.one.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue bookingNotificationsQueue() {
        return new Queue("booking.notifications.queue", true);
    }

    @Bean
    public Queue bookingCreatedQueue() {
        return new Queue("booking-created-queue", true);
    }

    @Bean
    public Queue bookingCanceledQueue() {
        return new Queue("booking-canceled-queue", true);
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange("notification-exchange");
    }

    @Bean
    public Binding bookingNotificationsBinding(Queue bookingNotificationsQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(bookingNotificationsQueue).to(notificationExchange).with("booking.notifications.routingKey");
    }
    
    @Bean
    public Binding bookingCreatedBinding(Queue bookingCreatedQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(bookingCreatedQueue).to(notificationExchange).with("booking.created");
    }
    
    @Bean
    public Binding bookingCanceledBinding(Queue bookingCanceledQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(bookingCanceledQueue).to(notificationExchange).with("booking.canceled");
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}