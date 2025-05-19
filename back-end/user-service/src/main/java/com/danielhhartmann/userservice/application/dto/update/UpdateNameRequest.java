package com.danielhhartmann.userservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateNameRequest(
        @Schema(example = "João da Silva", description = "Novo nome do usuário")
        String name
) {}
