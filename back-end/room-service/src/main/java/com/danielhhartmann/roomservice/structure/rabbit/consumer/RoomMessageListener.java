package com.danielhhartmann.roomservice.structure.rabbit.consumer;

// Use o DTO específico do RoomService aqui
import com.danielhhartmann.roomservice.application.dto.bookingService.RoomStatusResponse;
import com.danielhhartmann.roomservice.domain.RoomRepository;
import com.danielhhartmann.roomservice.domain.entity.Room; // Supondo que você tenha uma entidade Room
import com.danielhhartmann.roomservice.domain.enums.RoomStatus;
import com.danielhhartmann.roomservice.structure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload; // Opcional, mas bom para clareza
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomMessageListener {

    private final RoomRepository roomRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public RoomStatusResponse handleRoomStatusRequest(@Payload Long roomId) {
        log.info("Received room status request for roomId: {}", roomId);

        try {
            Optional<Room> roomOptional = roomRepository.findById(roomId);

            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                boolean isAvailable = room.getStatus().equals(RoomStatus.AVAILABLE);
                log.info("Room {} found. Status: {}, IsAvailable: {}", roomId, room.getStatus(), isAvailable);
                // Use o DTO do room-service
                return new com.danielhhartmann.roomservice.application.dto.bookingService.RoomStatusResponse(roomId, true, isAvailable);
            } else {
                log.warn("Room {} not found.", roomId);
                return new com.danielhhartmann.roomservice.application.dto.bookingService.RoomStatusResponse(roomId, false, false);
            }
        } catch (Exception e) {
            log.error("Error processing room status request for roomId {}: {}", roomId, e.getMessage(), e);
            return new com.danielhhartmann.roomservice.application.dto.bookingService.RoomStatusResponse(roomId, false, false);
        }
    }
}