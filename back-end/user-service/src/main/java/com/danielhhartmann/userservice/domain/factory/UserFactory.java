package com.danielhhartmann.userservice.domain.factory;

import com.danielhhartmann.userservice.domain.entity.User;
import com.danielhhartmann.userservice.domain.vo.Address;
import com.danielhhartmann.userservice.domain.vo.Cpf;
import com.danielhhartmann.userservice.domain.vo.Email;
import com.danielhhartmann.userservice.domain.vo.PhoneNumber;

public class UserFactory {
    public static User createUser(String name, String email, String cpf, String phone, String street, String number, String city, String cep) {
        Email emailVO = new Email(email);
        Cpf cpfVO = new Cpf(cpf);
        PhoneNumber phoneVO = new PhoneNumber(phone);
        Address addressVO = new Address(street, number, city, cep);

        User user = new User();
        user.setName(name);
        user.setEmail(emailVO);
        user.setCpf(cpfVO);
        user.setPhoneNumber(phoneVO);
        user.setAddress(addressVO);

        return user;
    }
}
