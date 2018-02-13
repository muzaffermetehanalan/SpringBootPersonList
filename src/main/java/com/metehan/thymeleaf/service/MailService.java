package com.metehan.thymeleaf.service;

import com.metehan.thymeleaf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Autowired
    private MailContentBuilder mailContentBuilder;


    public void sendEmail(Person person) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("prm-test@pia-team.com");
        messageHelper.setTo(person.getEmail());
        messageHelper.setSubject("Your personal details have been updated.");
        String content = mailContentBuilder.build(person);
        messageHelper.setText(content, true);
        try {
            javaMailSender.send(mimeMessage);
        }catch (MailException e){

        }
    }




}
