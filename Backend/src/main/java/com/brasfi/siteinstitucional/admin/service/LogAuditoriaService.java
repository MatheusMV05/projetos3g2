package com.brasfi.siteinstitucional.admin.service;

import com.brasfi.siteinstitucional.admin.dto.LogAuditoriaDTO;
import com.brasfi.siteinstitucional.admin.entity.LogAuditoria;
import com.brasfi.siteinstitucional.admin.repository.LogAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogAuditoriaService {

    private final LogAuditoriaRepository logRepository;

    public Page<LogAuditoriaDTO> buscarLogs(
            Long usuarioId,
            String entidade,
            String acao,
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable pageable) {

        Page<LogAuditoria> logs = logRepository.buscarComFiltros(
                usuarioId, entidade, acao, inicio, fim, pageable);

        return logs.map(this::converterParaDTO);
    }

    public Page<LogAuditoriaDTO> buscarUltimasAtividades(Pageable pageable) {
        // Criar um novo PageRequest com a ordenação por dataHora decrescente
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "dataHora")
        );

        Page<LogAuditoria> logs = logRepository.findAll(pageRequest);

        return logs.map(this::converterParaDTO);
    }

    public Page<LogAuditoriaDTO> buscarLogsPorUsuario(Long usuarioId, Pageable pageable) {
        Page<LogAuditoria> logs = logRepository.findByUsuarioId(usuarioId, pageable);

        return logs.map(this::converterParaDTO);
    }

    public LogAuditoriaDTO converterParaDTO(LogAuditoria log) {
        return LogAuditoriaDTO.builder()
                .id(log.getId())
                .usuarioId(log.getUsuario() != null ? log.getUsuario().getId() : null)
                .usuarioNome(log.getUsuario() != null ? log.getUsuario().getNome() : "Sistema")
                .acao(log.getAcao())
                .entidade(log.getEntidade())
                .entidadeId(log.getEntidadeId())
                .detalhes(log.getDetalhes())
                .ip(log.getIp())
                .dataHora(log.getDataHora())
                .build();
    }
}
