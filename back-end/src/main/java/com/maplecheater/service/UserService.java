package com.maplecheater.service;

import com.maplecheater.domain.dto.request.ChangeNicknameRequestData;
import com.maplecheater.domain.dto.request.ChangePasswordRequestData;
import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.EmailCheckResponseData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.role.RoleRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.EmailNotFoundException;
import com.maplecheater.exception.InvalidVerificationException;
import com.maplecheater.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이메일을 이용해서 인증된 사용자인지 확인하는 메서드
     *
     * @param email : 회원가입하려는 이메일
     * @return 이메일이 인증된 사용자라면 true 반환
     * @throws EmailNotFoundException
     */
    public boolean checkVerifiedEmail(String email) {
        VerificationType verified = emailVerificationRepository.findVerifiedByEmail(email);

        if(verified == null) { // 이메일 정보가 인증 관련 테이블에 없다면
            throw new EmailNotFoundException();
        }
        return verified.equals(VerificationType.VERIFIED);
    }

    /**
     * 존재하는 이메일인지 확인한다.
     *
     * @param email : 중복을 확인하려는 이메일
     * @return 중복된 이메일이라면 true
     */
    public EmailCheckResponseData isExistEmail(String email) {
        boolean isExist = userRepository.existsByEmail(email);

        return new EmailCheckResponseData(isExist);
    }

    /**
     * 회원가입을 진행한다.
     *
     * @param registerRequestData 회원의 정보가 담긴 DTO
     * @return 회원가입이 완료된 사용자의 이메일과 닉네임
     * @throws
     */
    public RegisterResponseData registerUser(RegisterRequestData registerRequestData) {
        String email = registerRequestData.getEmail();
        boolean verified = checkVerifiedEmail(email);

        if(!verified) { // 인증받지 않았다면
            throw new InvalidVerificationException();
        }

        User user = modelMapper.map(registerRequestData, User.class);
        user.register(passwordEncoder);
        User savedUser = userRepository.save(user);

        Role role = new Role(savedUser.getId(), RoleType.USER);

        roleRepository.save(role);

        return modelMapper.map(savedUser, RegisterResponseData.class);
    }

    /**
     * 비밀번호를 변경한다.
     *
     * @param targetId : 변경 하려는 userId
     * @param request : 기존의 비밀번호 ,새로운 비밀번호
     * @param tokenUserId : 토큰에 저장된 userId
     */
    public void changePassword(Long targetId,
                               ChangePasswordRequestData request,
                               Long tokenUserId) {

        if(!tokenUserId.equals(targetId)) {
            throw new AuthenticationFailedException();
        }

        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();

        User selectedUser = userRepository.findById(tokenUserId).orElseThrow(
                () -> new UserNotFoundException());

        selectedUser.changePassword(oldPassword, newPassword, passwordEncoder);
    }

    /**
     * 닉네임을 변경한다.
     *
     * @param targetId
     * @param changeNicknameRequestData 변경하려는 닉네임 dto
     * @param tokenUserId
     */
    public void changeNickname(Long targetId, ChangeNicknameRequestData changeNicknameRequestData, Long tokenUserId) {
        if(!tokenUserId.equals(targetId)) {
            throw new AuthenticationFailedException();
        }

        User selectedUser = userRepository.findById(tokenUserId).orElseThrow(
                () -> new UserNotFoundException());

        selectedUser.changeNickname(changeNicknameRequestData.getNewNickname());
    }
}
