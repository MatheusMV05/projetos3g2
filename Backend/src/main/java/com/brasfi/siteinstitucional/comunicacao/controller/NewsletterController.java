package com.brasfi.siteinstitucional.comunicacao.controller;

import com.brasfi.siteinstitucional.comunicacao.dto.AssinanteDTO;
import com.brasfi.siteinstitucional.comunicacao.dto.NewsletterDTO;
import com.brasfi.siteinstitucional.comunicacao.entity.Assinante;
import com.brasfi.siteinstitucional.comunicacao.entity.Newsletter;
import com.brasfi.siteinstitucional.comunicacao.service.NewsletterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NewsletterController {

    private final NewsletterService newsletterService;

    @PostMapping("/api/public/newsletter/inscrever")
    public ResponseEntity<Assinante> inscreverNewsletter(@Valid @RequestBody AssinanteDTO assinanteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsletterService.inscrever(assinanteDTO));
    }

    @DeleteMapping("/api/public/newsletter/cancelar")
    public ResponseEntity<Void> cancelarInscricao(@RequestParam String email) {
        newsletterService.cancelarInscricao(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/admin/newsletter/assinantes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Assinante>> listarAssinantes(
            @RequestParam(required = false) Boolean ativo,
            Pageable pageable) {

        return ResponseEntity.ok(newsletterService.listarAssinantes(ativo, pageable));
    }

    @PostMapping("/api/admin/newsletter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Newsletter> criarNewsletter(
            @Valid @RequestBody NewsletterDTO newsletterDTO,
            Authentication authentication) {

        Long usuarioId = ((com.brasfi.siteinstitucional.auth.entity.Usuario) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsletterService.criarNewsletter(newsletterDTO, usuarioId));
    }

    @PostMapping("/api/admin/newsletter/{id}/enviar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Newsletter> enviarNewsletter(@PathVariable Long id) {
        return ResponseEntity.ok(newsletterService.enviarNewsletter(id));
    }

    @GetMapping("/api/admin/newsletter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Newsletter>> listarNewsletters(
            @RequestParam(required = false) Boolean enviada,
            @RequestParam(required = false) Long usuarioId,
            Pageable pageable) {

        return ResponseEntity.ok(newsletterService.listarNewsletters(enviada, usuarioId, pageable));
    }

    @GetMapping("/api/admin/newsletter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Newsletter> buscarNewsletter(@PathVariable Long id) {
        return ResponseEntity.ok(newsletterService.buscarNewsletter(id));
    }
}