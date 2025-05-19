package com.danielhhartmann.roomservice.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class Description {

    @Column(name = "description", nullable = false)
    private String value;

    public Description(String value) {
        this.value = value;
    }
}
