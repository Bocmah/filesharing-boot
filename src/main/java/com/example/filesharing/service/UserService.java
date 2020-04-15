package com.example.filesharing.service;

import com.example.filesharing.domain.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User findByEmail(String email);
}
