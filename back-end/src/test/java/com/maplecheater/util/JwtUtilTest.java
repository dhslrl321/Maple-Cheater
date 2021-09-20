package com.maplecheater.util;

import com.maplecheater.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {


    private static final String SECRET = "66555555444444333333222222111111";
    private static final Long USER_ID = 1L;

    // 2021-10-04 에 만료되는 토큰
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    // 2021-10-04 에 만료되는 토큰
    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5Ns0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    // 2021-10-04 에 만료되는 토큰
    private static final String MAL_FORMED_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzIdfH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YDab83k";

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    @DisplayName("토큰 생성 테스트")
    void generateToken() {
        String token = jwtUtil.generateToken(USER_ID);

        assertNotNull(token);
    }

    @Test
    @DisplayName("토큰 파싱 테스트 - 성공")
    void parseToken_success() {
        Claims claims = jwtUtil.parseToken(VALID_TOKEN);

        Long userId = claims.get("userId", Long.class);

        assertEquals(USER_ID, userId);
    }

    @Test
    @DisplayName("토큰 파싱 테스트 - 실패 - Signature Exception")
    void parseToken_fail_signature() {
        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> jwtUtil.parseToken(INVALID_TOKEN));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("토큰 파싱 테스트 - 실패 - Malformed Exception")
    void parseToken_fail_malformed() {
        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> jwtUtil.parseToken(MAL_FORMED_TOKEN));

        assertNotNull(exception.getMessage());
    }

}