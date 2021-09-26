package com.maplecheater.util;

import com.maplecheater.domain.type.EmailTemplateType;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Component
public class MailUtil {

    private final JavaMailSender mailSender;

    /**
     * 인증 코드를 받아 HTML 형태의 인증 코드로 반환한다.
     *
     * @param code : 인증 코드
     * @param type : 회원가입인지 임시 비밀번호 발급인지 타입
     * @return MimeMessage : HTML 형태의 인증 코드 템플릿
     */
    public MimeMessage createMailTemplate(String code, EmailTemplateType type) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        String header = "회원가입 인증 코드 확인";
        String paragraph = "아래의 확인 코드를 Maple-Cheater 홈페이지에의 인증 코드에 입력하세요";

        if (type.equals(EmailTemplateType.TEMP_PASSWORD)) {
            header = "임시 비밀번호 발급";
            paragraph = "아래의 임시 비밀번호를 이용해서 로그인을 한 뒤, 비밀번호를 바꿔주세요";
        }

        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">";
        msg += header + "</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">";
        msg += paragraph + "</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";
        msg += "<a href=\"https://maplecheater.com\" style=\"text-decoration: none; color: #434245;\" rel=\"noreferrer noopener\" target=\"_blank\">Maple-Cheater</a>";

        message.setText(msg, "utf-8", "html");

        return message;
    }

    /**
     * 이메일 인증 코드를 생성한다.
     */
    public String generateVerifyCode() {
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(code);
    }

    /**
     * 임시 비밀번호를 생성한다.
     */
    public String generateTempPassword() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, 8);
    }
}
