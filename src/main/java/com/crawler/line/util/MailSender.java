package com.crawler.line.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSender {

    private final String senderId = "linetester@naver.com";
    private final String senderPwd = "!123123123a";

    public void send(String receiverEmail, String tempPassword) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.naver.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderId, senderPwd);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderId));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject("[안녕하세요] 임시비밀번호 드립니다.");
            message.setText("임시비밀번호는 [" + tempPassword + "] 입니다.^^ 변경 후 사용해 주세요.");
            Transport.send(message);
            log.info("Success Message Send");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
