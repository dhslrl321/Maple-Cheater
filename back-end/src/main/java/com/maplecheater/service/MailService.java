package com.maplecheater.service;

import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.UserExistsException;
import com.maplecheater.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MailService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final MailUtil mailUtil;

    /**
     * 이메일을 받아 메일 인증 코드를 생성하고 인증 메일을 보낸다.
     *
     * @param email : 인증을 수행할 메일
     * @throws UserExistsException : 이미 존재하는 회원일 경우
     */
    public void sendMail(String email) {

        String code = mailUtil.generateVerifyCode();
        MimeMessage message = null;

        validateEmail(email, code, emailVerificationRepository, userRepository);

        try {
            message = mailUtil.createMailTemplate(null);
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("Maple-Cheater 회원가입 인증 메일");
        } catch (Exception e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
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
