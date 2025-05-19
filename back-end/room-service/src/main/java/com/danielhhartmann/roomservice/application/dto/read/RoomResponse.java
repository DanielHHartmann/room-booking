package com.danielhhartmann.roomservice.application.dto.read;

import com.danielhhartmann.roomservice.domain.enums.RoomStatus;

public record RoomResponse(
        Long id,
        String name,
        Integer capacity,
        String description,
        RoomStatus status
) {}
