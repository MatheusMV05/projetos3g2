package com.brasfi.siteinstitucional.comunicacao.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String remetente;

    public void enviarEmailSimples(String destinatario, String assunto, String conteudo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(remetente);
        message.setTo(destinatario);
        message.setSubject(assunto);
        message.setText(conteudo);
        emailSender.send(message);
    }

    public void enviarEmailHtml(String destinatario, String assunto, String template,
                                Map<String, Object> variaveis) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariables(variaveis);
        String htmlConteudo = templateEngine.process(template, context);

        helper.setFrom(remetente);
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(htmlConteudo, true);

        emailSender.send(message);
    }

    public void enviarEmailEmMassa(List<String> destinatarios, String assunto, String template,
                                   Map<String, Object> variaveis) throws MessagingException {
        Context context = new Context();
        context.setVariables(variaveis);
        String htmlConteudo = templateEngine.process(template, context);

        for (String destinatario : destinatarios) {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(htmlConteudo, true);

            emailSender.send(message);
        }
    }
}