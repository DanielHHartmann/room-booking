package com.danielhhartmann.roomservice.application;

import com.danielhhartmann.roomservice.application.dto.update.*;
import com.danielhhartmann.roomservice.domain.RoomRepository;
import com.danielhhartmann.roomservice.domain.entity.Room;
import com.danielhhartmann.roomservice.domain.vo.Capacity;
import com.danielhhartmann.roomservice.domain.vo.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UpdateRoomUseCase {

    private final RoomRepository roomRepository;

    public void updateAll(Long id, UpdateRoomRequest request) {
        Room room = getRoomOrThrow(id);

        if (request.name() != null && !request.name().isBlank()) {
            room.setName(request.name().trim());
        }

        if (request.capacity() != null) {
            room.setCapacity(new Capacity(request.capacity()));
        }

        if (request.description() != null && !request.description().isBlank()) {
            room.setDescription(new Description(request.description()));
        }

        roomRepository.save(room);
    }

    public void updateName(Long id, UpdateNameRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
        }
        Room room = getRoomOrThrow(id);
        room.setName(request.name().trim());
        roomRepository.save(room);
    }

    public void updateCapacity(Long id, UpdateCapacityRequest request) {
        Room room = getRoomOrThrow(id);
        room.setCapacity(new Capacity(request.capacity()));
        roomRepository.save(room);
    }

    public void updateDescription(Long id, UpdateDescriptionRequest request) {
        if (request.description() == null || request.description().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description cannot be null or empty");
        }
        Room room = getRoomOrThrow(id);
        room.setDescription(new Description(request.description()));
        roomRepository.save(room);
    }

    private Room getRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with ID: " + id));
    }

    public void updateStatus(Long id, UpdateRoomStatusRequest request) {
        if (request.status() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status cannot be null");
        }

        Room room = getRoomOrThrow(id);
        room.setStatus(request.status());
        roomRepository.save(room);
    }

}
