package com.danielhhartmann.roomservice.application;

import com.danielhhartmann.roomservice.application.dto.create.CreateRoomRequest;
import com.danielhhartmann.roomservice.domain.RoomRepository;
import com.danielhhartmann.roomservice.domain.entity.Room;
import com.danielhhartmann.roomservice.domain.factory.RoomFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRoomUseCase {

    final private RoomRepository roomRepository;

    public Room execute(CreateRoomRequest request) {

        Room room = RoomFactory.createRoom(
                request.name(),
                request.capacity(),
                request.description()
        );

        return roomRepository.save(room);
    }
}
