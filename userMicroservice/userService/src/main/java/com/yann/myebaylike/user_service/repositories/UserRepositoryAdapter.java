package com.yann.myebaylike.user_service.repositories;

import com.yann.myebaylike.user_service.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

@Repository("UserRepository")
public class UserRepositoryAdapter implements UserRepository {

    private JpaUserRepository userRepository;

    public UserRepositoryAdapter(JpaUserRepository userRepository){

        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var user = userRepository.findByEmail(email);

        return Optional.ofNullable(user).map(this::toUser);
    }

    @Override
    public void save(User user) {
        Assert.notNull(user, "The user must be null when saving a user.");
        Assert.notNull(user.getEmail(), "The user email must not be null.");
        Assert.notNull(user.getName(), "The user name must not be null.");

        if(user.getId() == null){
            user.setId(UUID.randomUUID().toString());
        }

        var userObjectEntity = toUser(user);

        userRepository.save(userObjectEntity);
    }

    private User toUser(com.yann.myebaylike.user_service.repositories.models.User userObjectEntity){
        var user = new User();
        user.setEmail(userObjectEntity.getEmail());
        user.setId(userObjectEntity.getId());
        user.setImageUrl(userObjectEntity.getImageUrl());
        user.setName(userObjectEntity.getName());
        return user;
    }
    public com.yann.myebaylike.user_service.repositories.models.User toUser(User user){
        var userObjectEntity = new com.yann.myebaylike.user_service.repositories.models.User();
        userObjectEntity.setId(user.getId());
        userObjectEntity.setEmail(user.getEmail());
        userObjectEntity.setImageUrl(user.getImageUrl());
        userObjectEntity.setName(user.getName());
        return userObjectEntity;
    }
}
