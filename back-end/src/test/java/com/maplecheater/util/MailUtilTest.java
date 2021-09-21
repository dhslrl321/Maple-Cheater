package com.maplecheater.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MailUtilTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    @DisplayName("메일 전송 테스트")
    void sendMail() {
        List<String> users = new ArrayList<>();

        users.add("dhslrl321@gmail.com");
        users.add("1684031@pcu.ac.kr");

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo((String[]) users.toArray(new String[users.size()]));

        message.setSubject("Spring boot 메일 테스트");
        message.setText("메일이 잘 가는지 확인하기 위한 테스트 입니다");

        javaMailSender.send(message);
    }
}