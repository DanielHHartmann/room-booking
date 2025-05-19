package com.danielhhartmann.roomservice.application.dto.update;

import com.danielhhartmann.roomservice.domain.enums.RoomStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateRoomStatusRequest(
        @Schema(example = "INACTIVE") RoomStatus status
) {}