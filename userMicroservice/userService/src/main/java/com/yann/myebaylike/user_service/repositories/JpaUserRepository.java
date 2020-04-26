package com.yann.myebaylike.user_service.repositories;

import com.yann.myebaylike.user_service.repositories.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User save(User user);
}
