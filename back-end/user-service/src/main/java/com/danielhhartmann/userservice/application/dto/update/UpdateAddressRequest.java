package com.danielhhartmann.userservice.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateAddressRequest(
        @Schema(example = "Rua das Flores") String street,
        @Schema(example = "123") String number,
        @Schema(example = "São Paulo") String city,
        @Schema(example = "01001-000") String cep
) {}
