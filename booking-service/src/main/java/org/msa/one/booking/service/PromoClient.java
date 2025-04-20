package org.msa.one.booking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
public class PromoClient {
    private static final Logger log = LoggerFactory.getLogger(PromoClient.class);

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_PROMO = "promo-exchange";
    private static final String ROUTING_KEY_PROMO = "promo.events";

    // Явный конструктор
    public PromoClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPromoEvent(String userEmail) {
        log.info("Sending promo event for userEmail={}", userEmail);
        PromoEvent event = new PromoEvent(userEmail, System.currentTimeMillis());
        rabbitTemplate.convertAndSend(EXCHANGE_PROMO, ROUTING_KEY_PROMO, event);
    }

    public record PromoEvent(String userEmail, long timestamp) implements Serializable {}
}