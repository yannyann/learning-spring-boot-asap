package com.yann.myebaylike.user_service.services;

import com.yann.myebaylike.user_service.models.User;

public interface UserService {
    void registerUser(User user);
    void updateUser(User user);
}
