package com.danielhhartmann.bookingservice.presentation;

import com.danielhhartmann.bookingservice.application.*;
import com.danielhhartmann.bookingservice.application.dto.create.CreateBookingRequest;
import com.danielhhartmann.bookingservice.application.dto.read.BookingResponse;
import com.danielhhartmann.bookingservice.application.dto.update.UpdateBookingPeriodRequest;
import com.danielhhartmann.bookingservice.application.dto.update.UpdateBookingStatusRequest;
import com.danielhhartmann.bookingservice.domain.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final CreateBookingUseCase createBookingUseCase;
    private final ReadBookingUseCase readBookingUseCase;
    private final UpdateBookingUseCase updateBookingUseCase;

    @GetMapping
    public List<BookingResponse> getAllBookings(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return readBookingUseCase.findAll(page, size);
    }

    @GetMapping("/{id}")
    public BookingResponse getBookingById(@PathVariable Long id) {
        return readBookingUseCase.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponse> getBookingsByUserId(@PathVariable Long userId) {
        return readBookingUseCase.findByUserId(userId);
    }

    @GetMapping("/room/{roomId}")
    public List<BookingResponse> getBookingsByRoomId(@PathVariable Long roomId) {
        return readBookingUseCase.findByRoomId(roomId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody CreateBookingRequest request) {
        return createBookingUseCase.execute(request);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateBookingStatusRequest request
    ) {
        updateBookingUseCase.updateStatus(id, request);
    }

    @PatchMapping("/{id}/period")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePeriod(
            @PathVariable Long id,
            @RequestBody UpdateBookingPeriodRequest request
    ) {
        updateBookingUseCase.updatePeriod(id, request);
    }
}
