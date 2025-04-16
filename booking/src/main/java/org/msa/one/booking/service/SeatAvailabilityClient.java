package org.msa.one.booking.service;

import org.springframework.stereotype.Component;

/**
 * Клас-клієнт для взаємодії з SeatAvailabilityService (REST, Feign, тощо).
 */
@Component
public class SeatAvailabilityClient {

    public boolean checkAvailability(Long movieId, String seats) {
        // Виклик REST / Feign → SeatAvailabilityService
        return true;
    }

    public void reserveSeats(Long movieId, String seats) {
        // Виклик для резервування
    }

    public void releaseSeats(Long movieId, String seats) {
        // Виклик для звільнення
    }
}