package com.maplecheater.security;

import com.maplecheater.domain.entity.Role;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserAuthentication extends AbstractAuthenticationToken {

    private final Long userId;

    public UserAuthentication(Long userId, List<Role> roles) {
        super(authorities(roles));
        this.userId = userId;
    }

    private static List<? extends GrantedAuthority> authorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    public Long getUserid() {
        return userId;
    }
}
