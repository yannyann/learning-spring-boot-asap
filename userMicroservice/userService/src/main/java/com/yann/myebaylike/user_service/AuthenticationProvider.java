package com.yann.myebaylike.user_service;

import com.yann.myebaylike.user_service.models.User;
import com.yann.myebaylike.user_service.repositories.JpaUserRepository;
import com.yann.myebaylike.user_service.repositories.UserRepository;
import com.yann.myebaylike.user_service.repositories.UserRepositoryAdapter;
import com.yann.myebaylike.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Configuration
public class AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(WebClient rest) {
        var delegate = new DefaultOAuth2UserService();
        return request -> {
            var user = delegate.loadUser(request);

            Map attributes = user.getAttributes();
            var userInfo = new User();
            userInfo.setEmail((String) attributes.get("email"));
            userInfo.setId((String) attributes.get("sub"));
            userInfo.setImageUrl((String) attributes.get("picture"));
            userInfo.setName((String) attributes.get("name"));
            userService.updateUser(userInfo);

            return user;
        };
    }
/*

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService(WebClient rest, CustomOidcUserService customOidcUserService) {
        return request -> {
            var user = customOidcUserService.loadUser(request);

            return user;
        };
    }*/
}
