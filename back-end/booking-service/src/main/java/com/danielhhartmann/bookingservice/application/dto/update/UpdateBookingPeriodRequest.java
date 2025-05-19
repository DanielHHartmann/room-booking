package com.danielhhartmann.bookingservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UpdateBookingPeriodRequest(
        @Schema(example = "2024-12-01T08:00:00") LocalDateTime startDateTime,
        @Schema(example = "2024-12-01T10:00:00") LocalDateTime endDateTime
) {}
