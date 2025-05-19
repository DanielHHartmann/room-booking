package com.danielhhartmann.userservice.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Embeddable
@NoArgsConstructor
@Getter
public class Cpf {
    @Column(name = "cpf", nullable = false, unique = true)
    private String value;

    public Cpf(String value) {
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF is required");
        }

        if (!value.matches("\\d{11}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CPF format: " + value);
        }

        this.value = value;
    }
}
