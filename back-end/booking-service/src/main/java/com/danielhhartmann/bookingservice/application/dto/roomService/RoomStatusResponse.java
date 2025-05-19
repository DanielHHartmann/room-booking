package com.danielhhartmann.bookingservice.application.dto.roomService;

public record RoomStatusResponse(Long roomId, boolean found, boolean available) { }
