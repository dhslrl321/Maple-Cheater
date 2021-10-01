package com.maplecheater.util;

import com.maplecheater.domain.type.EmailTemplateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MailUtilTest {

    private MailUtil mailUtil;

    @BeforeEach
    void setUp() {
        mailUtil = new MailUtil(new JavaMailSenderImpl());
    }

    @Test
    @DisplayName("인증 코드 생성")
    void generateVerifyCode() {
        String code = mailUtil.generateVerifyCode();

        assertEquals(6, code.length());
    }

    @Test
    @DisplayName("메일 전송 테스트")
    void sendMail() {
        assertDoesNotThrow(() -> mailUtil.createMailTemplate("123456", EmailTemplateType.AUTHENTICATION));
    }

    @Test
    @DisplayName("임시 비밀번호 생성 테스트")
    void generateTempPassword() {
        String tempPassword = mailUtil.generateTempPassword();

        assertEquals(8, tempPassword.length());
    }

    @Test
    @DisplayName("adsf")
    void adsf() {
        System.out.println(LocalDateTime.now());
    }
}