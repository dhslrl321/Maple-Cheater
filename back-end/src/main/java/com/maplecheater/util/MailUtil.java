package com.maplecheater.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class MailUtil {

    private final JavaMailSender mailSender;

    public MimeMessage createMailTemplate(String code) throws Exception {

        code = code.substring(0, 3) + "-" + code.substring(3, 6);

        MimeMessage message = mailSender.createMimeMessage();

        String msg="";
        msg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"https://slack.com/x-a1607371436052/img/slack_logo_240.png\" alt=\"\" loading=\"lazy\">";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 Maple-Cheater 의 인증 코드에 입력하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";
        msg += "<a href=\"https://maplecheater.com\" style=\"text-decoration: none; color: #434245;\" rel=\"noreferrer noopener\" target=\"_blank\">Slack Clone Technologies, Inc</a>";

        message.setText(msg, "utf-8", "html");

        return message;
    }

    public String generateVerifyCode() {
        StringBuffer code = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            code.append(random.nextInt());
        }

        return code.toString();
    }
}
