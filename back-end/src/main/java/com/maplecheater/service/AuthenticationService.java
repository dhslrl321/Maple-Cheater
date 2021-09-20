package com.maplecheater.service;

import com.maplecheater.domain.dto.request.LoginRequestData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.role.RoleRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    /**
     * email 과 password 의 dto 를 받아서 로그인 인증을 수행한다.
     *
     * @param request : email, password dto
     * @return JwtUtil 이 반환하는 새로운 jwt token
     */
    public String login(LoginRequestData request) {

        String email = request.getEmail();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationFailedException());

        boolean authenticate = user.authenticate(password, passwordEncoder);

        if(!authenticate) { // 비밀번호가 다르면?
            throw new AuthenticationFailedException();
        }

        return jwtUtil.generateToken(user.getId());
    }

    /**
     * 토큰을 파싱하여 사용자 ID 를 반환한다.
     *
     * @param accessToken
     * @return userId
     */
    public Long parseToken(String accessToken) {
        Claims claims = jwtUtil.parseToken(accessToken);
        return claims.get("userId", Long.class);
    }


    /**
     * 회원 id 를 받아 권한을 조회한다.
     *
     * @param userId
     * @return 권한 목록
     */
    public List<Role> getRoles(Long userId) {
        return roleRepository.findAllByUserId(userId);
    }
}
