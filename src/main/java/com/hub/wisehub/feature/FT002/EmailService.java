package com.hub.wisehub.feature.FT002;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hub.wisehub.feature.UtilsService;

@Service
public class EmailService {
    
    @Value("${spring.mail.username}") private String sender;
    @Autowired private UtilsService utilsService;
    @Autowired private JavaMailSender emailSender;

    public ResponseEntity<ObjectNode> enviarEmail(String destinatario, String assunto, String mensagem) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(destinatario);
            email.setSubject(assunto);
            email.setText(mensagem);
            email.setFrom(sender);
            emailSender.send(email);
            return ResponseEntity.status(HttpStatus.OK).body(utilsService.response("Email enviado com sucesso !")); 
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(utilsService.response("Email enviado com sucesso !")); 
        }
    }

    public void enviarEmailCriacaoUsuario(String destinatario, String assunto, String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);
        email.setFrom(sender);
        emailSender.send(email);
    }
}
