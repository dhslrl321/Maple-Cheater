package com.maplecheater.security;

import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.type.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthenticationTest {

    private UserAuthentication userAuthentication;

    @BeforeEach
    void setUp() {
        userAuthentication = new UserAuthentication(1L, List.of(new Role(1L, RoleType.USER)));
    }

    @Test
    @DisplayName("getCredential")
    void getCredential() {
        assertNull(userAuthentication.getCredentials());
    }

    @Test
    @DisplayName("getUserId")
    void getUserId() {
        assertEquals(1L, userAuthentication.getUserid());
    }

    @Test
    @DisplayName("getPrincipal")
    void getPrincipal() {
        assertEquals(1L, userAuthentication.getPrincipal());
    }

}