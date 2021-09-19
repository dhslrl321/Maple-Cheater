package com.maplecheater.domain.repository.user;

import com.maplecheater.domain.entity.User;

public interface UserRepositoryCustom {
    User findByEmail(String email);
}
