package com.example.demo.site.services;

public interface MailService
{
    void sendMessage(String from, String to, String subject, String text);
}
