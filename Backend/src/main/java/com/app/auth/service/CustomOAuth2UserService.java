package com.app.auth.service;

import com.app.auth._enum.UserProvider;
import com.app.auth.entity.User;
import com.app.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        /*
        Business Logic Implementation Notes:
        When an existing user is found with the same email:
        Current implementation: Simply returns the existing user
        Potential enhancements:
        Throw exception if provider type conflicts (e.g., existing LOCAL provider)
        Merge provider types (add GOOGLE to existing LOCAL provider)
        Implement provider switching logic */
        userRepository.findByEmail(email).orElseGet(() -> saveOAuth2User(email, name, UserProvider.GOOGLE));

        return new DefaultOAuth2User(new ArrayList<>(), oauth2User.getAttributes(), "email");
    }

    private User saveOAuth2User(String email, String name, UserProvider userProvider) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setProvider(userProvider);
        return userRepository.save(user);
    }
}