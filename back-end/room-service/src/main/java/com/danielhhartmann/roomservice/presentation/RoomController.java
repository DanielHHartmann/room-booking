package com.danielhhartmann.roomservice.presentation;

import com.danielhhartmann.roomservice.application.CreateRoomUseCase;
import com.danielhhartmann.roomservice.application.ReadRoomUseCase;
import com.danielhhartmann.roomservice.application.UpdateRoomUseCase;
import com.danielhhartmann.roomservice.application.dto.update.*;
import com.danielhhartmann.roomservice.application.dto.create.CreateRoomRequest;
import com.danielhhartmann.roomservice.application.dto.read.RoomResponse;
import com.danielhhartmann.roomservice.domain.entity.Room;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Tag(name = "Rooms", description = "Operações para gerenciamento de salas")
public class RoomController {

    private final CreateRoomUseCase createRoomUserUseCase;
    private final ReadRoomUseCase readRoomUseCase;
    private final UpdateRoomUseCase updateRoomUseCase;

    // Criar nova sala
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar nova sala")
    public RoomResponse create(@Valid @RequestBody CreateRoomRequest request) {
        return toResponse(createRoomUserUseCase.execute(request));
    }

    // Buscar sala por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por ID")
    public RoomResponse getById(@PathVariable Long id) {
        return readRoomUseCase.findById(id);
    }

    // Listar todas as salas (com paginação)
    @GetMapping
    @Operation(summary = "Listar salas com paginação")
    public List<RoomResponse> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return readRoomUseCase.findAll(page, size);
    }

    // Atualização completa da sala
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar todos os dados da sala")
    public void updateAll(@PathVariable Long id, @Valid @RequestBody UpdateRoomRequest request) {
        updateRoomUseCase.updateAll(id, request);
    }

    // Atualizar apenas o nome
    @PatchMapping("/{id}/name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar nome da sala")
    public void updateName(@PathVariable Long id, @Valid @RequestBody UpdateNameRequest request) {
        updateRoomUseCase.updateName(id, request);
    }

    // Atualizar apenas a capacidade
    @PatchMapping("/{id}/capacity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar capacidade da sala")
    public void updateCapacity(@PathVariable Long id, @Valid @RequestBody UpdateCapacityRequest request) {
        updateRoomUseCase.updateCapacity(id, request);
    }

    // Atualizar apenas a descrição
    @PatchMapping("/{id}/description")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar descrição da sala")
    public void updateDescription(@PathVariable Long id, @Valid @RequestBody UpdateDescriptionRequest request) {
        updateRoomUseCase.updateDescription(id, request);
    }

    // Atualizar apenas o status
    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar status da sala")
    public void updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateRoomStatusRequest request) {
        updateRoomUseCase.updateStatus(id, request);
    }

    // Método auxiliar para transformar entidade em DTO
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
