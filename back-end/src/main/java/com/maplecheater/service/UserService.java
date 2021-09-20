package com.maplecheater.service;

import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.EmailNotFoundException;
import com.maplecheater.exception.InvalidVerificationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;

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
     * 회원가입을 진행한다.
     *
     * @param request 회원의 정보가 담긴 DTO
     * @return 회원가입이 완료된 사용자의 이메일과 닉네임
     * @throws
     */
    public RegisterResponseData registerUser(RegisterRequestData request) {
        String email = request.getEmail();
        boolean verified = checkVerifiedEmail(email);

        if(!verified) { // 인증받지 않았다면
            throw new InvalidVerificationException();
        }

        User user = modelMapper.map(request, User.class);
        user.register();

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, RegisterResponseData.class);
    }
}
