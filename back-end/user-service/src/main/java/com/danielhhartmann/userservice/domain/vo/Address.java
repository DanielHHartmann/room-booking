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
public class Address {
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "cep", nullable = false)
    private String cep;

    public Address(String street, String number, String city, String cep) {
        if (street == null || number == null || city == null || cep == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All address fields are required");
        }

        if (!cep.matches("\\d{5}-?\\d{3}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CEP format: " + cep);
        }

        this.street = street;
        this.number = number;
        this.city = city;
        this.cep = cep;
    }
}
