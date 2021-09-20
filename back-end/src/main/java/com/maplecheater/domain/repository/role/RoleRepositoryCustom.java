package com.maplecheater.domain.repository.role;

import com.maplecheater.domain.entity.Role;

import java.util.List;

public interface RoleRepositoryCustom {
    List<Role> findAllByUserId(Long userId);
}
