package com.maplecheater.domain.repository;

import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.repository.role.RoleRepository;
import com.maplecheater.domain.type.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class RoleRepositoryTest {

    private static final Long USER_ID = 1004L;
    private static final Long ADMIN_ID = 777L;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        Role user = new Role(USER_ID, RoleType.USER);
        Role admin_user = new Role(ADMIN_ID, RoleType.USER);
        Role admin_admin = new Role(ADMIN_ID, RoleType.ADMIN);

        roleRepository.save(user);
        roleRepository.save(admin_user);
        roleRepository.save(admin_admin);
    }

    @Test
    @DisplayName("userId가 가지고 있는 권한 확인")
    void findAllByUserId() {
        List<Role> roles = roleRepository.findAllByUserId(ADMIN_ID);

        assertEquals(2, roles.size());
    }
}