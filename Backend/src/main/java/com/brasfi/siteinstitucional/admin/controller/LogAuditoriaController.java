package com.brasfi.siteinstitucional.admin.controller;

import com.brasfi.siteinstitucional.admin.dto.LogAuditoriaDTO;
import com.brasfi.siteinstitucional.admin.service.LogAuditoriaService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin/auditoria")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class LogAuditoriaController {

    private final LogAuditoriaService logService;

    @GetMapping
    public ResponseEntity<Page<LogAuditoriaDTO>> listarLogs(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String entidade,
            @RequestParam(required = false) String acao,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Pageable pageable) {

        return ResponseEntity.ok(logService.buscarLogs(usuarioId, entidade, acao, inicio, fim, pageable));
    }

    @GetMapping("/ultimas-atividades")
    public ResponseEntity<Page<LogAuditoriaDTO>> ultimasAtividades(
            @RequestParam(defaultValue = "10") int limit,
            Pageable pageable) {

        return ResponseEntity.ok(logService.buscarUltimasAtividades(pageable));
    }

    @GetMapping("/por-usuario")
    public ResponseEntity<Page<LogAuditoriaDTO>> logsPorUsuario(
            @RequestParam @NotNull Long usuarioId,
            Pageable pageable) {

        return ResponseEntity.ok(logService.buscarLogsPorUsuario(usuarioId, pageable));
    }
}