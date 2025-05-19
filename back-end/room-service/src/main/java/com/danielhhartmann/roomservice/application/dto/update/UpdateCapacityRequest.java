package com.danielhhartmann.roomservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCapacityRequest(
        @Schema(example = "2", description = "Nova capacidade para a sala.")
        Integer capacity
) {}
