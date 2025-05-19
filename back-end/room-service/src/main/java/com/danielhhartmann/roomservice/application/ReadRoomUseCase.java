package com.danielhhartmann.roomservice.application;

import com.danielhhartmann.roomservice.application.dto.read.RoomResponse;
import com.danielhhartmann.roomservice.domain.RoomRepository;
import com.danielhhartmann.roomservice.domain.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadRoomUseCase {
    private final RoomRepository roomRepository;

    public RoomResponse findById(Long id) {
        return toResponse(roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with ID: " + id)));
    }

    public List<RoomResponse> findAll(Integer page, Integer size) {
        return roomRepository.findAll(page, size).stream()
                .map(this::toResponse)
                .toList();
    }

    private RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getRoomId(),
                room.getName(),
                room.getCapacity().getValue(),
                room.getDescription().getValue(),
                room.getStatus()
        );
    }
}
