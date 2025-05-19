package com.danielhhartmann.bookingservice.application.dto.read;

import com.danielhhartmann.bookingservice.domain.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Long userId,
        Long roomId,
        LocalDateTime startAt,
        LocalDateTime endAt,
        BookingStatus status
) {}
