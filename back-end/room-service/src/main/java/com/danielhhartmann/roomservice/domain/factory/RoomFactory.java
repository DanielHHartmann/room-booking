package com.danielhhartmann.roomservice.domain.factory;

import com.danielhhartmann.roomservice.domain.entity.Room;
import com.danielhhartmann.roomservice.domain.enums.RoomStatus;
import com.danielhhartmann.roomservice.domain.vo.Capacity;
import com.danielhhartmann.roomservice.domain.vo.Description;

public class RoomFactory {

    public static Room createRoom(String name, Integer capacity, String description) {
        Description descriptionVO = new Description(description);
        Capacity capacityVO = new Capacity(capacity);

        Room room = new Room();
        room.setName(name);
        room.setCapacity(capacityVO);
        room.setDescription(descriptionVO);
        room.setStatus(RoomStatus.AVAILABLE);

        return room;
    }
}
