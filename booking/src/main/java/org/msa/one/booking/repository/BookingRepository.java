package org.msa.one.booking.repository;

import org.msa.one.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Наприклад:
    List<Booking> findByUserEmail(String email);
}