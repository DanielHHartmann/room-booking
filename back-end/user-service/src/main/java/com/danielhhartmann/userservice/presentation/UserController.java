package com.danielhhartmann.userservice.presentation;

import com.danielhhartmann.userservice.application.*;
import com.danielhhartmann.userservice.application.dto.create.CreateUserRequest;
import com.danielhhartmann.userservice.application.dto.read.UserResponse;
import com.danielhhartmann.userservice.application.dto.update.*;
import com.danielhhartmann.userservice.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operações relacionadas a usuários")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final ReadUserUseCase readUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return toResponse(createUserUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UserResponse getById(@PathVariable Long id) {
        return readUserUseCase.findById(id);
    }

    @GetMapping("/email")
    @Operation(summary = "Buscar usuário por e-mail")
    public UserResponse getByEmail(@RequestParam String value) {
        return readUserUseCase.findByEmail(value);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários com paginação")
    public List<UserResponse> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return readUserUseCase.findAll(page, size);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar todos os dados do usuário")
    public void updateAll(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        updateUserUseCase.updateAll(id, request);
    }

    @PatchMapping("/{id}/name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar nome do usuário")
    public void updateName(@PathVariable Long id, @RequestBody UpdateNameRequest request) {
        updateUserUseCase.updateName(id, request);
    }

    @PatchMapping("/{id}/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar e-mail do usuário")
    public void updateEmail(@PathVariable Long id, @RequestBody UpdateEmailRequest request) {
        updateUserUseCase.updateEmail(id, request);
    }

    @PatchMapping("/{id}/cpf")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar CPF do usuário")
    public void updateCpf(@PathVariable Long id, @RequestBody UpdateCpfRequest request) {
        updateUserUseCase.updateCpf(id, request);
    }

    @PatchMapping("/{id}/phone")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar telefone do usuário")
    public void updatePhone(@PathVariable Long id, @RequestBody UpdatePhoneRequest request) {
        updateUserUseCase.updatePhone(id, request);
    }

    @PatchMapping("/{id}/address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualizar endereço do usuário")
    public void updateAddress(@PathVariable Long id, @RequestBody UpdateAddressRequest request) {
        updateUserUseCase.updateAddress(id, request);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail().getValue(),
                user.getCpf().getValue(),
                user.getPhoneNumber().getValue(),
                user.getAddress().getStreet(),
                user.getAddress().getNumber(),
                user.getAddress().getCity(),
                user.getAddress().getCep()
        );
    }
}
