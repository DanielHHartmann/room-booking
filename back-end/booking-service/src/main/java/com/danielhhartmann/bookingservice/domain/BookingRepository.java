package com.danielhhartmann.bookingservice.domain;

import com.danielhhartmann.bookingservice.domain.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {

    Optional<Booking> findById(Long bookingId);

    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByUserId(Long userId);

    List<Booking> findAll(Integer size, Integer page);

    Booking save(Booking booking);
}
