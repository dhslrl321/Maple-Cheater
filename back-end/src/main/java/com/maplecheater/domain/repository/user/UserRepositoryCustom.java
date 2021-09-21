package com.maplecheater.domain.repository.user;

import com.maplecheater.domain.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsAndNotUnregisteredByEmail(String email);
}
