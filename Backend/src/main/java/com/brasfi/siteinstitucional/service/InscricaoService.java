package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.entity.Evento;
import com.brasfi.siteinstitucional.entity.Inscricao;
import com.brasfi.siteinstitucional.entity.InscricaoStatus;
import com.brasfi.siteinstitucional.repository.EventoRepository;
import com.brasfi.siteinstitucional.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;
    private final EventoEmailService eventoEmailService;

    @Autowired
    public InscricaoService(InscricaoRepository inscricaoRepository,
                            EventoRepository eventoRepository,
                            EventoEmailService eventoEmailService) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoRepository = eventoRepository;
        this.eventoEmailService = eventoEmailService;
    }

    @Transactional
    public Inscricao realizarInscricao(Long eventoId, String nomeCompleto, String email) {
        // ... (código existente para realizarInscricao) ...
        // 1. Verificar se o evento existe
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado."));

        // 2. Verificar se o usuário já está inscrito no evento
        if (inscricaoRepository.existsByEventoIdAndEmail(eventoId, email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Você já está inscrito(a) neste evento.");
        }

        // 3. Verificar capacidade do evento (se houver limite)
        if (evento.getCapacidadeMaxima() != null && evento.getCapacidadeMaxima() > 0) {
            long inscricoesConfirmadas = inscricaoRepository.countByEventoIdAndStatus(eventoId, InscricaoStatus.CONFIRMADA);
            if (inscricoesConfirmadas >= evento.getCapacidadeMaxima()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O evento atingiu a capacidade máxima de inscrições.");
            }
        }

        // 4. Criar a inscrição
        Inscricao novaInscricao = new Inscricao();
        novaInscricao.setEvento(evento);
        novaInscricao.setNomeCompleto(nomeCompleto);
        novaInscricao.setEmail(email);
        novaInscricao.setDataInscricao(LocalDateTime.now());
        novaInscricao.setStatus(InscricaoStatus.CONFIRMADA); // Define como CONFIRMADA imediatamente

        Inscricao inscricaoSalva = inscricaoRepository.save(novaInscricao);

        // 5. Enviar e-mail de confirmação
        eventoEmailService.enviarEmailConfirmacao(inscricaoSalva);

        return inscricaoSalva;
    }

    public Optional<Inscricao> buscarInscricaoPorId(Long id) {
        return inscricaoRepository.findById(id);
    }

    public List<Inscricao> buscarInscricoesPorEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado."));
        return inscricaoRepository.findByEvento(evento);
    }

    @Transactional
    public Inscricao confirmarInscricao(Long inscricaoId) {
        // ... (código existente para confirmarInscricao) ...
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada."));

        if (inscricao.getStatus() == InscricaoStatus.CONFIRMADA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A inscrição já está confirmada.");
        }

        inscricao.setStatus(InscricaoStatus.CONFIRMADA);
        Inscricao inscricaoAtualizada = inscricaoRepository.save(inscricao);

        eventoEmailService.enviarEmailConfirmacao(inscricaoAtualizada);

        return inscricaoAtualizada;
    }

    @Transactional
    public void cancelarInscricao(Long inscricaoId) {
        // ... (código existente para cancelarInscricao) ...
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada."));

        if (inscricao.getStatus() == InscricaoStatus.CANCELADA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A inscrição já está cancelada.");
        }

        inscricao.setStatus(InscricaoStatus.CANCELADA);
        inscricaoRepository.save(inscricao);

        eventoEmailService.enviarEmailCancelamento(inscricao);
    }

    public List<Inscricao> findAllInscricoes() {
        return inscricaoRepository.findAll();
    }

    /**
     * Gera uma string CSV com a lista de participantes de um evento.
     * @param eventoId O ID do evento.
     * @return Uma string no formato CSV.
     */
    public String exportarParticipantesCsv(Long eventoId) {
        // Obtenha o evento para validação e nome do arquivo
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado."));

        // Obtenha todas as inscrições confirmadas para este evento
        List<Inscricao> participantes = inscricaoRepository.findByEventoAndStatus(evento, InscricaoStatus.CONFIRMADA);

        if (participantes.isEmpty()) {
            return "Não há participantes confirmados para este evento."; // Mensagem simples se não houver inscritos
        }

        StringBuilder csvBuilder = new StringBuilder();

        // Cabeçalho CSV
        csvBuilder.append("ID Inscrição,Nome Completo,Email,Data Inscrição,Status Inscrição\n");

        // Linhas de dados
        for (Inscricao inscricao : participantes) {
            csvBuilder.append(inscricao.getId()).append(",");
            csvBuilder.append("\"").append(inscricao.getNomeCompleto().replace("\"", "\"\"")).append("\","); // Escapar aspas duplas
            csvBuilder.append(inscricao.getEmail()).append(",");
            // Formatar a data para um padrão legível
            csvBuilder.append(inscricao.getDataInscricao().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append(",");
            csvBuilder.append(inscricao.getStatus().name());
            csvBuilder.append("\n");
        }

        return csvBuilder.toString();
    }
}