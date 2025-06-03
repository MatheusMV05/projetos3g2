package com.brasfi.siteinstitucional.parcerias.controller;

import com.brasfi.siteinstitucional.parcerias.dto.SolicitacaoParceriaCreateDTO;
import com.brasfi.siteinstitucional.parcerias.dto.SolicitacaoParceriaDTO;
import com.brasfi.siteinstitucional.parcerias.dto.SolicitacaoParceriaUpdateStatusDTO;
import com.brasfi.siteinstitucional.parcerias.entity.StatusSolicitacao;
import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import com.brasfi.siteinstitucional.parcerias.service.SolicitacaoParceriaService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class SolicitacaoParceriaController {

    private final SolicitacaoParceriaService solicitacaoService;

    @PostMapping("/public/parcerias/solicitar")
    public ResponseEntity<SolicitacaoParceriaDTO> criarSolicitacao(
            @Valid @RequestBody SolicitacaoParceriaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitacaoService.criarSolicitacao(dto));
    }

    @GetMapping("/admin/parcerias/solicitacoes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<SolicitacaoParceriaDTO>> listarSolicitacoes(
            @RequestParam(required = false) StatusSolicitacao status,
            @RequestParam(required = false) TipoParceria tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Pageable pageable) {
        return ResponseEntity.ok(solicitacaoService.listarSolicitacoes(status, tipo, inicio, fim, pageable));
    }

    @GetMapping("/admin/parcerias/solicitacoes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SolicitacaoParceriaDTO> buscarSolicitacaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitacaoService.buscarPorId(id));
    }

    @PatchMapping("/admin/parcerias/solicitacoes/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SolicitacaoParceriaDTO> atualizarStatusSolicitacao(
            @PathVariable Long id,
            @Valid @RequestBody SolicitacaoParceriaUpdateStatusDTO dto) {
        return ResponseEntity.ok(solicitacaoService.atualizarStatusSolicitacao(id, dto));
    }
}