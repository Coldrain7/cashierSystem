package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.MailProperties;
import com.example.mybatisplus.model.dto.MailDTO;
import com.github.dozermapper.core.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

import java.io.IOException;
@Service
public class MailService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Mapper mapper;

    public boolean sendSimpleMailMessage(MailDTO mailDTO) {
        SimpleMailMessage simpleMailMessage = mapper.map(mailDTO, SimpleMailMessage.class);
        if (StringUtils.isEmpty(mailDTO.getFrom())) {
            mailDTO.setFrom(mailProperties.getFrom());
        }
        try {
            javaMailSender.send(simpleMailMessage);
            // System.out.println("发送成功");
            return true;
        } catch (MailException ex) {
            System.err.println("Failed to send email: " + ex.getMessage());
            return false;
            // 在这里，您可以根据需要进一步处理异常，例如记录日志或通知用户。
        }
    }

    public void sendMimeMessage(MailDTO mailDTO) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);

            if (StringUtils.isEmpty(mailDTO.getFrom())) {
                messageHelper.setFrom(mailProperties.getFrom());
            }
            messageHelper.setTo(mailDTO.getTo());
            messageHelper.setSubject(mailDTO.getSubject());

            mimeMessage = messageHelper.getMimeMessage();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mailDTO.getText(), "text/html;charset=UTF-8");

            // 描述数据关系
            MimeMultipart mm = new MimeMultipart();
            mm.setSubType("related");
            mm.addBodyPart(mimeBodyPart);

            // 添加邮件附件
            for (String filename : mailDTO.getFilenames()) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mm.addBodyPart(attachPart);
            }
            mimeMessage.setContent(mm);
            mimeMessage.saveChanges();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }
}

