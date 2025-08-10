package com.poly.service;




import com.poly.model.MailInfo;

import jakarta.mail.MessagingException;

public interface MailerService {
    void send(MailInfo mail) throws MessagingException;
    
    void send(String to, String subject, String body,String anh) throws MessagingException;

    void queue(MailInfo mail);

    void queue(String to, String subject, String body,String anh);
}
