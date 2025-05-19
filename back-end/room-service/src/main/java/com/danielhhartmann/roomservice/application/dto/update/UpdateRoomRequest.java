package com.danielhhartmann.roomservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateRoomRequest(
        @Schema(example = "Sala B - Anexo A") String name,
        @Schema(example = "10") Integer capacity,
        @Schema(example = "Sala supimpa maneira 2.0") String description
) {}
