package org.sid.ebankingbackend.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void send(SimpleMailMessage mail);
}
