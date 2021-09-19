package com.maplecheater.service;

import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private static final String EMAIL = "test@test.com";

    private UserRepository userRepository = mock(UserRepository.class);
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        User user = User.builder()
                .id(1L)
                .email(EMAIL)
                .password("password")
                .nickname("닉네임")
                .created_at(LocalDateTime.now())
                .build();

        given(userRepository.save(any(User.class))).willReturn(user);
        given(userRepository.findByEmail(EMAIL)).willReturn(user);
    }

}