package com.danielhhartmann.roomservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateDescriptionRequest(
        @Schema(example = "Descrição criativa daora", description = "Nova descrição para a sala.")
        String description
) {}
