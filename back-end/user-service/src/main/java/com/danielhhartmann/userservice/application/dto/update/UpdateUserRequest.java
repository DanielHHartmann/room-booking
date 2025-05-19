package com.danielhhartmann.userservice.application.dto.update;

import com.danielhhartmann.userservice.application.dto.create.AddressRequest;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserRequest(
        @Schema(example = "João da Silva") String name,
        @Schema(example = "joao@email.com") String email,
        @Schema(example = "12345678900") String cpf,
        @Schema(example = "+5511987654321") String phone,
        AddressRequest address
) {}
