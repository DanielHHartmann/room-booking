package com.danielhhartmann.userservice.application.dto.read;

public record UserResponse(
        Long id,
        String name,
        String email,
        String cpf,
        String phone,
        String street,
        String number,
        String city,
        String cep
) {}
