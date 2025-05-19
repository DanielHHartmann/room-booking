package com.danielhhartmann.roomservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateNameRequest(
        @Schema(example = "Sala 52", description = "Novo nome da sala")
        String name
) {}
