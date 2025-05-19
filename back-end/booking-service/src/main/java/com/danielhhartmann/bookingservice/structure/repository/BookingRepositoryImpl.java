package com.danielhhartmann.bookingservice.structure.repository;

import com.danielhhartmann.bookingservice.domain.BookingRepository;
import com.danielhhartmann.bookingservice.domain.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private final JpaBookingRepository jpa;

    @Override
    public Optional<Booking> findById(Long bookingId) {
        return jpa.findById(bookingId);
    }

    @Override
    public List<Booking> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 10
        );
        return jpa.findAll(pageable).getContent();
    }

    @Override
    public Booking save(Booking booking) {
        return jpa.save(booking);
    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        return jpa.findByUserId(userId);
    }

    @Override
    public List<Booking> findByRoomId(Long roomId) {
        return jpa.findByRoomId(roomId);
    }
}
