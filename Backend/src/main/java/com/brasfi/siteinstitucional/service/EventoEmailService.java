package com.brasfi.siteinstitucional.service; // Adjust package

import org.springframework.stereotype.Service;
import com.brasfi.siteinstitucional.entity.Inscricao;
import com.brasfi.siteinstitucional.entity.Evento;

@Service
public class EventoEmailService {

    public void enviarEmailConfirmacao(Inscricao inscricao) {
        System.out.println("--- SIMULANDO ENVIO DE E-MAIL ---");
        System.out.println("Para: " + inscricao.getEmail());
        System.out.println("Assunto: Confirmação de Inscrição no Evento: " + inscricao.getEvento().getTitulo());
        System.out.println("Corpo: Olá " + inscricao.getNomeCompleto() + ",");
        System.out.println("Sua inscrição para o evento '" + inscricao.getEvento().getTitulo() + "' foi confirmada!");
        System.out.println("Data: " + inscricao.getEvento().getDataInicio());
        System.out.println("Local: " + inscricao.getEvento().getLocal());
        System.out.println("Status da Inscrição: " + inscricao.getStatus());
        System.out.println("Aguardamos sua presença!");
        System.out.println("-----------------------------------");
    }

    public void enviarEmailCancelamento(Inscricao inscricao) {
        System.out.println("--- SIMULANDO ENVIO DE E-MAIL DE CANCELAMENTO ---");
        System.out.println("Para: " + inscricao.getEmail());
        System.out.println("Assunto: Cancelamento de Inscrição no Evento: " + inscricao.getEvento().getTitulo());
        System.out.println("Corpo: Olá " + inscricao.getNomeCompleto() + ",");
        System.out.println("Sua inscrição para o evento '" + inscricao.getEvento().getTitulo() + "' foi cancelada.");
        System.out.println("-----------------------------------");
    }

    // New method for sending reminder emails
    public void enviarEmailLembrete(Inscricao inscricao) {
        Evento evento = inscricao.getEvento();
        System.out.println("--- SIMULANDO ENVIO DE E-MAIL DE LEMBRETE ---");
        System.out.println("Para: " + inscricao.getEmail());
        System.out.println("Assunto: Lembrete: Seu Evento Está Chegando! - " + evento.getTitulo());
        System.out.println("Corpo: Olá " + inscricao.getNomeCompleto() + ",");
        System.out.println("Este é um lembrete amigável de que o evento '" + evento.getTitulo() + "' está se aproximando!");
        System.out.println("Data: " + evento.getDataInicio());
        System.out.println("Local: " + evento.getLocal());
        System.out.println("Prepare-se para participar!");
        System.out.println("-----------------------------------");
    }
}