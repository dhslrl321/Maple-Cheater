package com.maplecheater.service;

import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.EmailTemplateType;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.UserExistsException;
import com.maplecheater.exception.UserNotFoundException;
import com.maplecheater.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MailService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final MailUtil mailUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이메일을 받아 메일 인증 코드를 생성하고 인증 메일을 보낸다.
     *
     * @param email : 인증을 수행할 메일
     * @throws UserExistsException : 이미 존재하는 회원일 경우
     */
    public void sendAuthMail(String email) {

        String code = mailUtil.generateVerifyCode();
        MimeMessage message = null;

        validateEmail(email, code, emailVerificationRepository, userRepository);

        try {
            message = mailUtil.createMailTemplate(code, EmailTemplateType.AUTHENTICATION);
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("Maple-Cheater 회원가입 인증 메일");
        } catch (Exception e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

    /**
     * 임시 비밀번호를 메일로 전송한다.
     *
     * @param email : 임시 비밀번호를 발급할 메일
     * @throws UserNotFoundException : 임시 비밀번호를 발급할 사용자가 존재하지 않는 경우
     */
    public void sendTempPasswordMail(String email) {

        String tempPassword = mailUtil.generateTempPassword();
        MimeMessage message = null;

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

        user.changePasswordForTempPassword(tempPassword, passwordEncoder);

        userRepository.save(user);

        try {
            message = mailUtil.createMailTemplate(tempPassword, EmailTemplateType.TEMP_PASSWORD);
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("Maple-Cheater 임시 비밀번호 전송 메일");
        } catch (Exception e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

    /**
     * 이메일로 전송된 code 와 사용자가 입력한 code 가 일치하는지 확인한다.
     *
     * @param email : 사용자의 email
     * @param code : 사용자가 입력한 code
     */
    public void authenticate(String email, String code) {
        EmailVerification userVerification = emailVerificationRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());

        String realCode = userVerification.getCode();

        if (!realCode.equals(code)) {
            throw new AuthenticationFailedException();
        }

        userVerification.verify();
        emailVerificationRepository.save(userVerification);
    }

    /**
     * 메일 인증 이력을 파악한다.
     *
     * @param email 이력을 파악할 이메일
     * @param code 인증 코드
     * @param emailVerificationRepository repository
     * @param userRepository repository
     * @return
     */
    private static EmailVerification validateEmail(String email,
                                                   String code,
                                                   EmailVerificationRepository emailVerificationRepository,
                                                   UserRepository userRepository) {

        Optional<EmailVerification> optionalVerification = emailVerificationRepository.findByEmail(email);

        if (optionalVerification.isPresent()) { // 존재한다면?
            boolean isUnregisteredUser = userRepository.existsAndNotUnregisteredByEmail(email);
            if(isUnregisteredUser) { // 이미 회원이라면 예외
                throw new UserExistsException();
            }

            EmailVerification emailVerification = optionalVerification.get();
            emailVerification.refreshCode(code);

            return emailVerificationRepository.save(emailVerification);
        }

        EmailVerification emailVerification = EmailVerification.builder()
                .email(email)
                .authDate(LocalDateTime.now())
                .code(code)
                .verified(VerificationType.UNVERIFIED)
                .build();

        return emailVerificationRepository.save(emailVerification);
    }
}
