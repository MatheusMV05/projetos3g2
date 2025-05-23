package com.brasfi.siteinstitucional.scheduler; // Adjust package

import com.brasfi.siteinstitucional.entity.Evento;
import com.brasfi.siteinstitucional.entity.Inscricao;
import com.brasfi.siteinstitucional.entity.InscricaoStatus;
import com.brasfi.siteinstitucional.repository.InscricaoRepository;
import com.brasfi.siteinstitucional.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LembreteScheduler {

    private final InscricaoRepository inscricaoRepository;
    private final EmailService emailService;

    @Autowired
    public LembreteScheduler(InscricaoRepository inscricaoRepository, EmailService emailService) {
        this.inscricaoRepository = inscricaoRepository;
        this.emailService = emailService;
    }

    /**
     * Este método será executado uma vez por dia, à meia-noite (00:00).
     * Ele busca por eventos que ocorrerão nos próximos X dias (ex: 7 dias)
     * e envia lembretes para os inscritos confirmados.
     */
    // @Scheduled(cron = "0 0 0 * * ?") // Executa todo dia à meia-noite (00:00:00)
    @Scheduled(fixedRate = 600000) // Para testes: executa a cada 10 minutos (600.000 ms)
    public void enviarLembretesAutomaticos() {
        System.out.println("Executando tarefa agendada: enviarLembretesAutomaticos");

        LocalDate hoje = LocalDate.now();
        // Definir quantos dias antes do evento o lembrete deve ser enviado
        // Ex: 7 dias antes. Ajuste conforme a necessidade.
        LocalDate dataLimiteLembrete = hoje.plusDays(7);

        // Busca todas as inscrições confirmadas para eventos que estão próximos
        List<Inscricao> inscricoesParaLembrete = inscricaoRepository.findAll().stream()
                .filter(inscricao -> inscricao.getStatus() == InscricaoStatus.CONFIRMADA)
                .filter(inscricao -> {
                    Evento evento = inscricao.getEvento();
                    return evento != null &&
                            evento.getDataInicio().isAfter(hoje) && // Evento ainda não começou
                            evento.getDataInicio().isBefore(dataLimiteLembrete); // E está dentro do período do lembrete
                })
                .toList(); // Usa toList() para Java 16+, para versões anteriores use .collect(Collectors.toList())


        if (inscricoesParaLembrete.isEmpty()) {
            System.out.println("Nenhum lembrete para enviar nesta rodada.");
        } else {
            System.out.println("Encontradas " + inscricoesParaLembrete.size() + " inscrições para enviar lembretes.");
            for (Inscricao inscricao : inscricoesParaLembrete) {
                emailService.enviarEmailLembrete(inscricao);
            }
        }
    }
}