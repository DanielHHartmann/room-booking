package com.danielhhartmann.bookingservice.application;

import com.danielhhartmann.bookingservice.application.dto.create.CreateBookingRequest;
import com.danielhhartmann.bookingservice.application.dto.roomService.RoomStatusResponse;
import com.danielhhartmann.bookingservice.application.dto.userService.UserResponse;
import com.danielhhartmann.bookingservice.domain.BookingRepository;
import com.danielhhartmann.bookingservice.domain.entity.Booking;
import com.danielhhartmann.bookingservice.domain.factory.BookingFactory;
import com.danielhhartmann.bookingservice.domain.vo.Period;
import com.danielhhartmann.bookingservice.structure.rabbit.messaging.RoomStatusRequester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBookingUseCase {

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final RoomStatusRequester roomStatusRequester;

    public Booking execute(CreateBookingRequest request) {
        log.info("Attempting to create booking for user {} and room {}", request.userId(), request.roomId());

        try {
            restTemplate.getForObject(
                    "http://user-service/users/" + request.userId(),
                    UserResponse.class
            );
            log.info("User {} found.", request.userId());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("User {} not found via user-service.", request.userId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            log.error("Failed to fetch user {} from user-service: {}", request.userId(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to fetch user from user-service");
        } catch (Exception e) {
            log.error("Error connecting to user-service for user {}: {}", request.userId(), e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error connecting to user-service");
        }


        RoomStatusResponse roomStatus;
        try {
            roomStatus = roomStatusRequester.sendRoomStatusRequest(request.roomId());
            log.info("Room status for room {}: found={}, available={}", request.roomId(), roomStatus.found(), roomStatus.available());
        } catch (IllegalStateException | IllegalArgumentException e) {
            log.error("Error obtaining room status for room {}: {}", request.roomId(), e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to get room status: " + e.getMessage());
        }


        if (!roomStatus.found()) {
            log.warn("Room {} does not exist.", request.roomId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room " + request.roomId() + " does not exist.");
        }
        if (!roomStatus.available()) {
            log.warn("Room {} is not available.", request.roomId());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Room " + request.roomId() + " is not available.");
        }

        Period newPeriod = new Period(request.startAt(), request.endAt());
        List<Booking> existingBookings = bookingRepository.findByRoomId(request.roomId());

        boolean hasConflict = existingBookings.stream()
                .anyMatch(existing -> existing.getPeriod().overlaps(newPeriod));

        if (hasConflict) {
            log.warn("Time conflict for room {} and period {}-{}", request.roomId(), request.startAt(), request.endAt());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There is already a booking for this room during the specified period.");
        }

        Booking booking = BookingFactory.createBooking(
                request.userId(),
                request.roomId(),
                request.startAt(),
                request.endAt()
        );

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created successfully with ID: {}", savedBooking.getId());
        return savedBooking;
    }
}