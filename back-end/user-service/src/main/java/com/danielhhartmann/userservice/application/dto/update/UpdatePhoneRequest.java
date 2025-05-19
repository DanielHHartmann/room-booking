package com.danielhhartmann.userservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePhoneRequest(
        @Schema(example = "+5511987654321", description = "Novo número de telefone")
        String phone
) {}
