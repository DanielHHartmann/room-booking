package com.danielhhartmann.bookingservice.application.dto.update;

import com.danielhhartmann.bookingservice.domain.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UpdateBookingRequest(
        @Schema(example = "1") Long userId,
        @Schema(example = "2") Long roomId,
        @Schema(example = "2024-12-01T08:00:00") LocalDateTime startDateTime,
        @Schema(example = "2024-12-01T10:00:00") LocalDateTime endDateTime,
        @Schema(example = "CONFIRMED") BookingStatus status
) {}
