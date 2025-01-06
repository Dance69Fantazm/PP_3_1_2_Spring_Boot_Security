package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;

public interface UserService {

    List<User> findAllUsers();

    void saveUser(User user, Set<Role> roles);

    User getUserById(long id);

    User getUserInfo();

    void updateUser(Long id, User user);

    void deleteUser(Long id);
}