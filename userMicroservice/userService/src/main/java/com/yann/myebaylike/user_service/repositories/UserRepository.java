package com.yann.myebaylike.user_service.repositories;

import com.yann.myebaylike.user_service.models.User;

import java.util.Optional;


public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
}
