package org.msa.one.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private LocalDateTime showTime;
    private Long movieId;
    private String bookingCode;
    private String seats;

    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
    private String status; // Наприклад: "ACTIVE" або "CANCELED"

    /**
     * Обов’язковий публічний конструктор без аргументів
     * (необхідний для JPA і десеріалізації).
     */
    public Booking() {
    }

    /**
     * Додатковий конструктор з ключовими полями (необов’язковий).
     */
    public Booking(String userEmail,
                   LocalDateTime showTime,
                   Long movieId,
                   String bookingCode,
                   String seats) {
        this.userEmail = userEmail;
        this.showTime = showTime;
        this.movieId = movieId;
        this.bookingCode = bookingCode;
        this.seats = seats;
    }

    // Гетери/сетери на кожне поле

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(LocalDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
