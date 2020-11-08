package com.example.demo.site.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class DefaultMailService implements MailService
{
    @Inject
    private JavaMailSender emailSender;

    @Override
    public void sendMessage(String from, String to, String subject, String text)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
