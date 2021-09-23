package com.maplecheater.domain.entity;

import com.maplecheater.exception.AuthenticationFailedException;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    /**
     * 회원가입 시에 호출이 되는데, 현재 시간을 기준으로 registeredAt 의 시간을 채운다.
     */
    public void register(PasswordEncoder passwordEncoder) {
        this.registeredAt = LocalDateTime.now();
        this.password = passwordEncoder.encode(password);
    }

    /**
     * 비밀번호를 변경한다.
     */
    public void changePassword(String oldPassword, String newPassword, PasswordEncoder passwordEncoder) { ;
        boolean auth = authenticate(oldPassword, passwordEncoder);
        if(!auth) {
            throw new AuthenticationFailedException();
        }
        this.password = passwordEncoder.encode(newPassword);
    }

    /**
     * 기존 비밀번호 인증을 생략하고 비밀번호를 변경한다.
     * 이메일 임시 비밀번호 발급에만 사용할 것
     */
    public void changePasswordForTempPassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);
    }

    /**
     * request 로 들어온 비빌번호와 비교한다.
     */
    public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }

    /**
     * 닉네임을 변경한다.
     *
     * @param newNickname 변경할 닉네임
     */
    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    /**
     * 회원을 탈퇴한다.
     */
    public void unregister() {
        this.unregisteredAt = LocalDateTime.now();
    }
}
