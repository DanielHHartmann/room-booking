package com.danielhhartmann.userservice.structure.repository;

import com.danielhhartmann.userservice.domain.UserRepository;
import com.danielhhartmann.userservice.domain.entity.User;
import com.danielhhartmann.userservice.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpa;

    @Override
    public Optional<User> findById(Long userId) {
        return jpa.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpa.findByEmail(email);
    }

    @Override
    public List<User> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 10
        );
        return jpa.findAll(pageable).getContent();
    }

    @Override
    public User save(User user) {
        return jpa.save(user);
    }
}
