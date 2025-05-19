package com.danielhhartmann.roomservice.domain;

import com.danielhhartmann.roomservice.domain.entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    Optional<Room> findById(Long roomId);
    List<Room> findAll(Integer size, Integer page);
    Room save(Room room);

}
