package com.danielhhartmann.bookingservice.application.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CreateBookingRequest(
        @Schema(example = "1") Long userId,
        @Schema(example = "5") Long roomId,
        @Schema(example = "2025-06-01T09:00:00") LocalDateTime startAt,
        @Schema(example = "2025-06-01T11:00:00") LocalDateTime endAt
) {}
