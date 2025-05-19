package com.danielhhartmann.bookingservice.structure.rabbit.messaging;

import com.danielhhartmann.bookingservice.application.dto.roomService.RoomStatusResponse;
import com.danielhhartmann.bookingservice.structure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomStatusRequester {

    private final RabbitTemplate rabbitTemplate;

    public RoomStatusResponse sendRoomStatusRequest(Long roomId) {
        log.info("Sending room status request for roomId: {}", roomId);
        Object response = rabbitTemplate.convertSendAndReceive(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                roomId
        );

        if (response == null) {
            log.error("No response received from Room Service for roomId: {}. Request might have timed out.", roomId);
            throw new IllegalStateException("No response received from Room Service (timeout or other issue).");
        }

        log.info("Received response from Room Service: {}", response);

        if (response instanceof com.danielhhartmann.bookingservice.application.dto.roomService.RoomStatusResponse brs) {
            return brs;
        }

        if (response instanceof Map<?, ?> map) {
            try {
                Object mapRoomIdObj = map.get("roomId");
                Object mapFoundObj = map.get("found");
                Object mapAvailableObj = map.get("available");

                if (mapRoomIdObj == null || mapFoundObj == null || mapAvailableObj == null) {
                    log.error("Incomplete map data received from Room Service: {}", map);
                    throw new IllegalArgumentException("Incomplete data in Room Service response map: " + map);
                }

                Long returnedRoomId = ((Number) mapRoomIdObj).longValue();
                boolean found = Boolean.parseBoolean(mapFoundObj.toString());
                boolean available = Boolean.parseBoolean(mapAvailableObj.toString());

                return new com.danielhhartmann.bookingservice.application.dto.roomService.RoomStatusResponse(returnedRoomId, found, available);
            } catch (ClassCastException | NumberFormatException e) {
                log.error("Error converting map response from Room Service. Map: {}, Error: {}", map, e.getMessage(), e);
                throw new IllegalArgumentException("Error converting map response from Room Service", e);
            }
        }

        log.error("Unexpected response type received from Room Service: {}. Content: {}", response.getClass().getName(), response);
        throw new IllegalStateException("Unexpected response type received from Room Service: " + response.getClass().getName());
    }
}