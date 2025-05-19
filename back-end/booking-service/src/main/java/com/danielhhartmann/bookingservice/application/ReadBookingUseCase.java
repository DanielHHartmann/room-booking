package com.danielhhartmann.bookingservice.application;

import com.danielhhartmann.bookingservice.application.dto.read.BookingResponse;
import com.danielhhartmann.bookingservice.domain.BookingRepository;
import com.danielhhartmann.bookingservice.domain.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadBookingUseCase {

    private final BookingRepository bookingRepository;

    public BookingResponse findById(Long id) {
        return toResponse(bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found with ID: " + id)));
    }

    public List<BookingResponse> findAll(Integer page, Integer size) {
        return bookingRepository.findAll(page, size).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BookingResponse> findByUserId(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BookingResponse> findByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId).stream()
                .map(this::toResponse)
                .toList();
    }

    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getUserId(),
                booking.getRoomId(),
                booking.getPeriod().getStartDateTime(),
                booking.getPeriod().getEndDateTime(),
                booking.getStatus()
        );
    }
}
