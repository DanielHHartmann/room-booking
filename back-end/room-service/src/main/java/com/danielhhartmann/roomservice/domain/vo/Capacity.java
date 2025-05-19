package com.danielhhartmann.roomservice.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Embeddable
@NoArgsConstructor
@Getter
public class Capacity {

    @Column(name = "capacity", nullable = false)
    private Integer value;

    public Capacity(Integer value) {
        if (value < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Capacity cannot be negative.");

        this.value = value;
    }
}
