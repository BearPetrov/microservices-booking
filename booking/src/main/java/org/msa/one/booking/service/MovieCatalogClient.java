package org.msa.one.booking.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Клас-клієнт для взаємодії з MovieCatalogService.
 */
@Component
public class MovieCatalogClient {

    public void validateShowtime(Long movieId, LocalDateTime showTime) {
        // Виклик REST / Feign → MovieCatalogService
        // Якщо сеанс відсутній → кинути виняток
    }
}