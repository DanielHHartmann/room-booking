package com.danielhhartmann.bookingservice.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@Getter
public class Period {

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endDateTime;

    public Period(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end date/time must not be null.");
        }

        if (!startDateTime.isBefore(endDateTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date/time must be before end date/time.");
        }

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    public boolean overlaps(Period other) {
        return startDateTime.isBefore(other.getEndDateTime()) &&
                endDateTime.isAfter(other.getStartDateTime());
    }

}
