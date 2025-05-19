package com.danielhhartmann.bookingservice.structure.repository;

import com.danielhhartmann.bookingservice.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JpaBookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByUserId(Long userId);
}
