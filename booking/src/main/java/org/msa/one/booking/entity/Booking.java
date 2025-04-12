

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

    // Обов'язково публічний конструктор без аргументів
    public Booking() {
    }

    // (Необов’язково) додатковий конструктор із усіма полями
    public Booking(String userEmail, LocalDateTime showTime, Long movieId,
                   String bookingCode, String seats) {
        this.userEmail = userEmail;
        this.showTime = showTime;
        this.movieId = movieId;
        this.bookingCode = bookingCode;
        this.seats = seats;
    }

    // Getter & Setter на кожне поле
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
}
