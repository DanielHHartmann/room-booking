package com.danielhhartmann.roomservice.structure.repository;

import com.danielhhartmann.roomservice.domain.RoomRepository;
import com.danielhhartmann.roomservice.domain.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

    private final JpaRoomRepository jpa;

    @Override
    public Optional<Room> findById(Long userId) {
        return jpa.findById(userId);
    }

    @Override
    public List<Room> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 10
        );
        return jpa.findAll(pageable).getContent();
    }

    @Override
    public Room save(Room room) {
        return jpa.save(room);
    }
}
