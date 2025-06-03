package com.brasfi.siteinstitucional.comunicacao.controller;

import com.brasfi.siteinstitucional.comunicacao.dto.MensagemDTO;
import com.brasfi.siteinstitucional.comunicacao.dto.RespostaContatoDTO;
import com.brasfi.siteinstitucional.comunicacao.entity.Mensagem;
import com.brasfi.siteinstitucional.comunicacao.service.ContatoService;
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
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;

    @PostMapping("/api/public/contato")
    public ResponseEntity<Void> enviarMensagem(@Valid @RequestBody MensagemDTO mensagemDTO) {
        contatoService.registrarMensagem(mensagemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/admin/contato")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Mensagem>> listarMensagens(
            @RequestParam(required = false) Boolean lida,
            @RequestParam(required = false) Boolean respondida,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Pageable pageable) {

        return ResponseEntity.ok(contatoService.listarMensagens(lida, respondida, inicio, fim, pageable));
    }

    @GetMapping("/api/admin/contato/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mensagem> buscarMensagem(@PathVariable Long id) {
        return ResponseEntity.ok(contatoService.buscarPorId(id));
    }

    @PatchMapping("/api/admin/contato/{id}/marcar-lida")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mensagem> marcarComoLida(@PathVariable Long id) {
        return ResponseEntity.ok(contatoService.marcarComoLida(id));
    }

    @PostMapping("/api/admin/contato/{id}/responder")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mensagem> responderMensagem(
            @PathVariable Long id,
            @Valid @RequestBody RespostaContatoDTO respostaDTO) {

        return ResponseEntity.ok(contatoService.responderMensagem(id, respostaDTO));
    }
}