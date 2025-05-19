package com.danielhhartmann.userservice.domain.entity;

import com.danielhhartmann.userservice.domain.vo.Address;
import com.danielhhartmann.userservice.domain.vo.Cpf;
import com.danielhhartmann.userservice.domain.vo.Email;
import com.danielhhartmann.userservice.domain.vo.PhoneNumber;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users", schema = "user_db")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Email email;

    @Embedded
    private Cpf cpf;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private Address address;
}
