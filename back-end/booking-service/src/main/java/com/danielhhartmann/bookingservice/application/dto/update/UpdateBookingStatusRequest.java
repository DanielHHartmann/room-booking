package com.danielhhartmann.bookingservice.application.dto.update;

import com.danielhhartmann.bookingservice.domain.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateBookingStatusRequest(
        @Schema(example = "CONFIRMED") BookingStatus status
) {}
