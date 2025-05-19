package com.danielhhartmann.userservice.application;

import com.danielhhartmann.userservice.application.dto.update.*;
import com.danielhhartmann.userservice.domain.UserRepository;
import com.danielhhartmann.userservice.domain.entity.User;
import com.danielhhartmann.userservice.domain.vo.Address;
import com.danielhhartmann.userservice.domain.vo.Cpf;
import com.danielhhartmann.userservice.domain.vo.Email;
import com.danielhhartmann.userservice.domain.vo.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    public void updateAll(Long id, UpdateUserRequest request) {
        User user = getUserOrThrow(id);

        if (request.name() != null && !request.name().isBlank()) {
            user.setName(request.name().trim());
        }

        if (request.email() != null) {
            user.setEmail(new Email(request.email()));
        }

        if (request.cpf() != null) {
            user.setCpf(new Cpf(request.cpf()));
        }

        if (request.phone() != null) {
            user.setPhoneNumber(new PhoneNumber(request.phone()));
        }

        if (request.address() != null) {
            Address updated = getAddress(request, user);

            user.setAddress(updated);
        }

        userRepository.save(user);
    }

    private static Address getAddress(UpdateUserRequest request, User user) {
        Address current = user.getAddress();

        return new Address(
                request.address().street() != null ? request.address().street() : current.getStreet(),
                request.address().number() != null ? request.address().number() : current.getNumber(),
                request.address().city() != null ? request.address().city() : current.getCity(),
                request.address().cep() != null ? request.address().cep() : current.getCep()
        );
    }


    public void updateName(Long id, UpdateNameRequest request) {
        User user = getUserOrThrow(id);
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
        }
        user.setName(request.name().trim());
        userRepository.save(user);
    }

    public void updateEmail(Long id, UpdateEmailRequest request) {
        User user = getUserOrThrow(id);
        user.setEmail(new Email(request.email()));
        userRepository.save(user);
    }

    public void updateCpf(Long id, UpdateCpfRequest request) {
        User user = getUserOrThrow(id);
        user.setCpf(new Cpf(request.cpf()));
        userRepository.save(user);
    }

    public void updatePhone(Long id, UpdatePhoneRequest request) {
        User user = getUserOrThrow(id);
        user.setPhoneNumber(new PhoneNumber(request.phone()));
        userRepository.save(user);
    }

    public void updateAddress(Long id, UpdateAddressRequest request) {
        User user = getUserOrThrow(id);

        Address current = user.getAddress();
        Address updated = new Address(
                request.street() != null ? request.street() : current.getStreet(),
                request.number() != null ? request.number() : current.getNumber(),
                request.city() != null ? request.city() : current.getCity(),
                request.cep() != null ? request.cep() : current.getCep()
        );

        user.setAddress(updated);
        userRepository.save(user);
    }

    public User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));
    }
}
