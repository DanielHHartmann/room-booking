package com.danielhhartmann.userservice.domain;

import com.danielhhartmann.userservice.domain.entity.User;
import com.danielhhartmann.userservice.domain.vo.Email;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long userId);
    Optional<User> findByEmail(Email email);
    List<User> findAll(Integer size, Integer page);
    User save(User user);

}
