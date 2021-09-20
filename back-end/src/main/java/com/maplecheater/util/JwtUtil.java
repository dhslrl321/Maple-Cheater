package com.maplecheater.util;

import com.maplecheater.exception.InvalidTokenException;
import com.maplecheater.exception.TokenExpiredException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    private final int ONE_DAY = 1000 * 24 * 60 * 60;

    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 사용자 id를 받아서 2주 기간의 토큰을 생성한다.
     *
     * @param userId Long 타입의 사용자 id
     * @return 생성된 Jwt 토큰
     */
    public String generateToken(Long userId) {
        Long now = new Date().getTime();

        Date expiredAt = new Date(now + (ONE_DAY * 14));

        return Jwts.builder()
                .claim("userId", userId)
                .setExpiration(expiredAt)
                .signWith(key)
                .compact();
    }

    /**
     * 토큰 string 을 받아서 Claims를 반환한다.
     *
     * @param token : 인코딩된 jwt 토큰
     * @return : 복호화된 토큰의 Claims
     * @throws InvalidTokenException 토큰이 유효하지 않을 때의 예외
     * @throws TokenExpiredException 토큰이 만료되었을 때의 예외
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new InvalidTokenException();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        }
    }
}
