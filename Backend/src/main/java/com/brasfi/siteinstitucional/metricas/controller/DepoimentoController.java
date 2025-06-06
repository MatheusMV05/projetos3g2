package com.brasfi.siteinstitucional.metricas.controller;

import com.brasfi.siteinstitucional.metricas.dto.DepoimentoDTO;
import com.brasfi.siteinstitucional.metricas.entity.Depoimento;
import com.brasfi.siteinstitucional.metricas.service.DepoimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepoimentoController {

    private final DepoimentoService depoimentoService;

    // Endpoints públicos
    @GetMapping("/api/public/depoimentos")
    public ResponseEntity<List<Depoimento>> listarDepoimentosAtivos() {
        return ResponseEntity.ok(depoimentoService.listarAtivos());
    }

    @GetMapping("/api/public/depoimentos/destaques")
    public ResponseEntity<List<Depoimento>> listarDepoimentosDestaque() {
        return ResponseEntity.ok(depoimentoService.listarDestaques());
    }

    @GetMapping("/api/public/depoimentos/ano/{ano}")
    public ResponseEntity<List<Depoimento>> listarDepoimentosPorAno(@PathVariable Integer ano) {
        return ResponseEntity.ok(depoimentoService.listarPorAno(ano));
    }

    @GetMapping("/api/public/depoimentos/organizacao/{organizacao}")
    public ResponseEntity<List<Depoimento>> listarDepoimentosPorOrganizacao(@PathVariable String organizacao) {
        return ResponseEntity.ok(depoimentoService.listarPorOrganizacao(organizacao));
    }

    @GetMapping("/api/public/depoimentos/anos")
    public ResponseEntity<List<Integer>> listarAnosDisponiveis() {
        return ResponseEntity.ok(depoimentoService.listarAnosDisponiveis());
    }

    // Endpoints administrativos
    @GetMapping("/api/admin/depoimentos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Depoimento>> buscarDepoimentos(
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String organizacao,
            @RequestParam(required = false) Boolean destaque,
            @RequestParam(required = false) Boolean ativo,
            Pageable pageable) {
        return ResponseEntity.ok(depoimentoService.buscarComFiltros(ano, organizacao, destaque, ativo, pageable));
    }

    @GetMapping("/api/admin/depoimentos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Depoimento> buscarDepoimentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(depoimentoService.buscarPorId(id));
    }

    @PostMapping("/api/admin/depoimentos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Depoimento> criarDepoimento(@Valid @RequestBody DepoimentoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(depoimentoService.criar(dto));
    }

    @PutMapping("/api/admin/depoimentos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Depoimento> atualizarDepoimento(
            @PathVariable Long id,
            @Valid @RequestBody DepoimentoDTO dto) {
        return ResponseEntity.ok(depoimentoService.atualizar(id, dto));
    }

    @DeleteMapping("/api/admin/depoimentos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirDepoimento(@PathVariable Long id) {
        depoimentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/admin/depoimentos/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Depoimento> ativarDepoimento(@PathVariable Long id) {
        return ResponseEntity.ok(depoimentoService.ativar(id));
    }

    @PatchMapping("/api/admin/depoimentos/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Depoimento> desativarDepoimento(@PathVariable Long id) {
        return ResponseEntity.ok(depoimentoService.desativar(id));
    }

    @PatchMapping("/api/admin/depoimentos/{id}/destaque")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Depoimento> alterarDestaque(
            @PathVariable Long id,
            @RequestParam boolean destaque) {
        return ResponseEntity.ok(depoimentoService.alterarDestaque(id, destaque));
    }
}