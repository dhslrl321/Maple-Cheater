package com.maplecheater.domain.repository;

import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 생성 테스트")
    void create_user() {
        LocalDateTime now = LocalDateTime.now();

        String nickname = "Hk123";
        String password = "test123";
        String email = "test@test.com";

        User user = User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .registeredAt(now)
                .build();

        User savedUser = userRepository.save(user);

        assertAll(
                () -> assertNotNull(user.getId()),
                () -> assertEquals(email, savedUser.getEmail()),
                () -> assertEquals(password, savedUser.getPassword()),
                () -> assertEquals(nickname, savedUser.getNickname()),
                () -> assertEquals(now, savedUser.getRegisteredAt())
        );
    }

    @Test
    @DisplayName("Email 로 사용자 찾기")
    void findByEmail() {
        generateUser(5, userRepository);

        String email = "test_0@test.com";

        User user = userRepository.findByEmail(email).get();

        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("Email 중복 검색")
    void existByEmail() {
        generateUser(5, userRepository);

        String exist = "test_4@test.com";
        String notExist = "test_5@test.com";

        assertAll(
                () -> assertTrue(userRepository.existsByEmail(exist)),
                () -> assertFalse(userRepository.existsByEmail(notExist))
        );
    }

    @Test
    @DisplayName("존재하면서 탈퇴하지 않은 회원인 경우")
    void existsAndNotUnregisteredByEmail() {
        generateUser(1, userRepository);

        String email = "test_0@test.com";

        boolean existsAndNotUnregistered = userRepository.existsAndNotUnregisteredByEmail(email);

        assertTrue(existsAndNotUnregistered);
    }


    private static void generateUser(int count, UserRepository userRepository) {
        for (int i = 0; i < count; i++) {
            User user = User.builder()
                    .email("test_" + i + "@test.com")
                    .password("test931" + i)
                    .nickname("사용자_" + i)
                    .registeredAt(LocalDateTime.now())
                    .build();

            userRepository.save(user);
        }
    }

}