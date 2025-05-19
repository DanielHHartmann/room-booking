package com.danielhhartmann.userservice.application;

import com.danielhhartmann.userservice.application.dto.read.UserResponse;
import com.danielhhartmann.userservice.domain.UserRepository;
import com.danielhhartmann.userservice.domain.entity.User;
import com.danielhhartmann.userservice.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadUserUseCase {
    private final UserRepository userRepository;

    public UserResponse findById(Long id) {
        return toResponse(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id)));
    }

    public UserResponse findByEmail(String email) {
        Email emailVO = new Email(email);
        return toResponse(userRepository.findByEmail(emailVO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email)));
    }

    public List<UserResponse> findAll(Integer page, Integer size) {
        return userRepository.findAll(page, size).stream()
                .map(this::toResponse)
                .toList();
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
