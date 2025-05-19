package com.danielhhartmann.roomservice.domain.entity;

import com.danielhhartmann.roomservice.domain.enums.RoomStatus;
import com.danielhhartmann.roomservice.domain.vo.Capacity;
import com.danielhhartmann.roomservice.domain.vo.Description;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "rooms", schema = "room_db")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false, unique = true)
    private Long roomId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Embedded
    private Capacity capacity;

    @Embedded
    private Description description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.AVAILABLE;
}
