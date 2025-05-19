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
public class PhoneNumber {

    @Column(name = "phone_number", nullable = false)
    private String value;

    public PhoneNumber(String rawValue) {
        if (rawValue == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number is required");
        }

        String digitsOnly = rawValue.replaceAll("\\D", "");

        if (digitsOnly.length() < 10 || digitsOnly.length() > 13) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number format: " + rawValue);
        }

        this.value = digitsOnly;
    }

}
