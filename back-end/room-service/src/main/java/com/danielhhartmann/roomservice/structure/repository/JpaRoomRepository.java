package com.danielhhartmann.roomservice.structure.repository;

import com.danielhhartmann.roomservice.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoomRepository extends JpaRepository<Room, Long> {
}
