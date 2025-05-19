package com.danielhhartmann.userservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateEmailRequest(
        @Schema(example = "joao@email.com", description = "Novo e-mail do usuário")
        String email
) {}
