package com.brasfi.siteinstitucional.admin.scheduling.controller;

import com.brasfi.siteinstitucional.admin.scheduling.dto.PublicacaoAgendadaDTO;
import com.brasfi.siteinstitucional.admin.scheduling.entity.PublicacaoAgendada;
import com.brasfi.siteinstitucional.admin.scheduling.service.PublicacaoAgendadaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin/agendamentos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PublicacaoAgendadaController {

    private final PublicacaoAgendadaService publicacaoService;

    @PostMapping
    public ResponseEntity<PublicacaoAgendada> agendar(@Valid @RequestBody PublicacaoAgendadaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoService.agendar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacaoAgendada> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(publicacaoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacaoAgendada> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PublicacaoAgendadaDTO dto) {
        return ResponseEntity.ok(publicacaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        publicacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<PublicacaoAgendada>> listar(
            @RequestParam(required = false) String tipoConteudo,
            @RequestParam(required = false) Boolean publicado,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            Pageable pageable) {

        return ResponseEntity.ok(publicacaoService.listar(
                tipoConteudo, publicado, usuarioId, dataInicio, dataFim, pageable));
    }
}