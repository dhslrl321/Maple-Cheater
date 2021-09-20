package com.maplecheater.domain.repository.role;

import com.maplecheater.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {
}
