package com.danielhhartmann.userservice.application;

import com.danielhhartmann.userservice.application.dto.create.AddressRequest;
import com.danielhhartmann.userservice.application.dto.create.CreateUserRequest;
import com.danielhhartmann.userservice.domain.UserRepository;
import com.danielhhartmann.userservice.domain.entity.User;
import com.danielhhartmann.userservice.domain.factory.UserFactory;
import com.danielhhartmann.userservice.domain.vo.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    final private UserRepository userRepository;

    public User execute(CreateUserRequest request) {
        AddressRequest address = request.address();

        User user = UserFactory.createUser(
                request.name(),
                request.email(),
                request.cpf(),
                request.phone(),
                address.street(),
                address.number(),
                address.city(),
                address.cep()
        );

        return userRepository.save(user);
    }

}
