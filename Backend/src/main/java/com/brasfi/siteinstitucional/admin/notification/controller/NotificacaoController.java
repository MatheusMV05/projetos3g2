package com.brasfi.siteinstitucional.admin.notification.controller;

import com.brasfi.siteinstitucional.admin.notification.dto.NotificacaoDTO;
import com.brasfi.siteinstitucional.admin.notification.service.NotificacaoService;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @GetMapping
    public ResponseEntity<Page<NotificacaoDTO>> listarNotificacoes(
            @RequestParam(required = false) Boolean lida,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Pageable pageable,
            Authentication authentication) {

        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();

        return ResponseEntity.ok(notificacaoService.listarNotificacoes(
                usuarioId, lida, tipo, inicio, fim, pageable));
    }

    @GetMapping("/nao-lidas")
    public ResponseEntity<List<NotificacaoDTO>> listarUltimasNaoLidas(Authentication authentication) {
        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(notificacaoService.listarUltimasNaoLidas(usuarioId));
    }

    @GetMapping("/contador")
    public ResponseEntity<Long> contarNaoLidas(Authentication authentication) {
        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(notificacaoService.contarNaoLidas(usuarioId));
    }

    @PatchMapping("/{id}/marcar-lida")
    public ResponseEntity<NotificacaoDTO> marcarComoLida(
            @PathVariable Long id,
            Authentication authentication) {

        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(notificacaoService.marcarComoLida(id, usuarioId));
    }

    @PatchMapping("/marcar-todas-lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(Authentication authentication) {
        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();
        notificacaoService.marcarTodasComoLidas(usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirNotificacao(
            @PathVariable Long id,
            Authentication authentication) {

        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();
        notificacaoService.excluirNotificacao(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}