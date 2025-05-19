package com.danielhhartmann.userservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCpfRequest(
        @Schema(example = "12345678900", description = "Novo CPF (apenas números)")
        String cpf
) {}
