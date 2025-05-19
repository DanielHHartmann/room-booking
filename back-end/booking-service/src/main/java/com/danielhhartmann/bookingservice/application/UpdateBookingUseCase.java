package com.danielhhartmann.bookingservice.application;

import com.danielhhartmann.bookingservice.application.dto.update.UpdateBookingPeriodRequest;
import com.danielhhartmann.bookingservice.application.dto.update.UpdateBookingStatusRequest;
import com.danielhhartmann.bookingservice.domain.BookingRepository;
import com.danielhhartmann.bookingservice.domain.entity.Booking;
import com.danielhhartmann.bookingservice.domain.vo.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UpdateBookingUseCase {

    private final BookingRepository bookingRepository;

    public void updateStatus(Long id, UpdateBookingStatusRequest request) {
        if (request.status() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status cannot be null");
        }

        Booking booking = getBookingOrThrow(id);
        booking.setStatus(request.status());
        bookingRepository.save(booking);
    }

    public void updatePeriod(Long id, UpdateBookingPeriodRequest request) {
        if (request.startDateTime() == null || request.endDateTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end datetime must be provided");
        }

        Booking booking = getBookingOrThrow(id);

        try {
            booking.setPeriod(new Period(request.startDateTime(), request.endDateTime()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        bookingRepository.save(booking);
    }

    private Booking getBookingOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found with ID: " + id));
    }
}
