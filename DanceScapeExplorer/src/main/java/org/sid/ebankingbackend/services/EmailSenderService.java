package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Dancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailToDancer(Dancer dancer, String subject, String body) {
        String dancerEmail = dancer.getDemail();

        if (dancerEmail != null && !dancerEmail.isEmpty()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("kardounmolkal@gmail.com"); // Modifier avec votre adresse e-mail
            message.setTo(dancerEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("Mail envoyé à " + dancerEmail);
        } else {
            System.out.println("Impossible d'envoyer un e-mail à Dancer : adresse e-mail manquante");
        }
    }


}
