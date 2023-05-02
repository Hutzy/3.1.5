package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    User getUser(String username);

    void delete(Long id);

    List<User> getUsers();

    void newUser(User user);

    void updateUser(Long id, User user);
}
