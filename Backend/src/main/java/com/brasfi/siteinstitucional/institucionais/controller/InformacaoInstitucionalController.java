package com.brasfi.siteinstitucional.institucionais.controller;

import com.brasfi.siteinstitucional.institucionais.dto.InformacaoInstitucionalDTO;
import com.brasfi.siteinstitucional.institucionais.entity.InformacaoInstitucional;
import com.brasfi.siteinstitucional.institucionais.service.InformacaoInstitucionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class InformacaoInstitucionalController {

    private final InformacaoInstitucionalService infoService;

    @GetMapping("/api/public/institucionais/{chave}")
    public ResponseEntity<InformacaoInstitucional> buscarPorChave(@PathVariable String chave) {
        return ResponseEntity.ok(infoService.buscarPorChave(chave));
    }

    @GetMapping("/api/public/institucionais")
    public ResponseEntity<List<InformacaoInstitucional>> listarAtivas() {
        return ResponseEntity.ok(infoService.listarAtivas());
    }

    @GetMapping("/api/public/institucionais/tipo/{tipo}")
    public ResponseEntity<List<InformacaoInstitucional>> listarAtivasPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(infoService.listarAtivasPorTipo(tipo));
    }

    @GetMapping("/api/public/institucionais/mapa")
    public ResponseEntity<Map<String, String>> listarAtivasComoMapa() {
        return ResponseEntity.ok(infoService.listarAtivasComoMapa());
    }

    @GetMapping("/api/public/institucionais/mapa/tipo/{tipo}")
    public ResponseEntity<Map<String, String>> listarAtivasComoMapaPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(infoService.listarAtivasComoMapaPorTipo(tipo));
    }

    @GetMapping("/api/admin/institucionais")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InformacaoInstitucional>> listarTodas() {
        return ResponseEntity.ok(infoService.listarTodas());
    }

    @PostMapping("/api/admin/institucionais")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InformacaoInstitucional> criar(@Valid @RequestBody InformacaoInstitucionalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(infoService.criar(dto));
    }

    @PutMapping("/api/admin/institucionais/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InformacaoInstitucional> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody InformacaoInstitucionalDTO dto) {
        return ResponseEntity.ok(infoService.atualizar(id, dto));
    }

    @DeleteMapping("/api/admin/institucionais/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        infoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/admin/institucionais/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InformacaoInstitucional> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(infoService.alterarEstado(id, true));
    }

    @PatchMapping("/api/admin/institucionais/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InformacaoInstitucional> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(infoService.alterarEstado(id, false));
    }
}