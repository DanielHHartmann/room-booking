package com.danielhhartmann.roomservice.application.dto.bookingService;

public record RoomStatusResponse(Long roomId, boolean found, boolean available) {}