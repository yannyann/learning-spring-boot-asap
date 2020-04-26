package com.yann.myebaylike.user_service.services;

import com.yann.myebaylike.user_service.models.User;
import com.yann.myebaylike.user_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User userInfo) {
        var optionalUser = userRepository.findByEmail(userInfo.getEmail());

        if(!optionalUser.isPresent()){
            createUser(userInfo);
        }
        else{
            updateUser(userInfo);
        }

        userRepository.save(userInfo);
    }

    private void createUser(User userInfo){
        var user = new User();
        user.setEmail(userInfo.getEmail());
        user.setImageUrl(userInfo.getImageUrl());
        user.setName(userInfo.getName());

        userRepository.save(user);
    }

    @Override
    public void updateUser(User userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail()).orElse(new User());
        user.setEmail(userInfo.getEmail());
        user.setImageUrl(userInfo.getImageUrl());
        user.setName(userInfo.getName());
        //user.setUserType(UserType.google);
        userRepository.save(user);
    }
}
