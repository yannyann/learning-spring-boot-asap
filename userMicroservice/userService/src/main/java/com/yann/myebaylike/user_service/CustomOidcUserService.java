package com.yann.myebaylike.user_service;

import com.yann.myebaylike.user_service.models.User;
import com.yann.myebaylike.user_service.repositories.UserRepository;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOidcUserService extends OidcUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        var l = userRepository;
        System.out.println("BONJOURRR");
        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = oidcUser.getAttributes();
        User userInfo = new User();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setId((String) attributes.get("sub"));
        userInfo.setImageUrl((String) attributes.get("picture"));
        userInfo.setName((String) attributes.get("name"));
        updateUser(userInfo);
        return oidcUser;
    }

    private void updateUser(User userInfo) {
        var user = userRepository.findByEmail(userInfo.getEmail()).orElse(new User());

        user.setEmail(userInfo.getEmail());
        user.setImageUrl(userInfo.getImageUrl());
        user.setName(userInfo.getName());
        //user.setUserType(UserType.google);
        userRepository.save(user);
    }
}
