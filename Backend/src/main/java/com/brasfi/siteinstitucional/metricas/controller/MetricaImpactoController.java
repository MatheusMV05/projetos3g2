package com.brasfi.siteinstitucional.metricas.controller;

import com.brasfi.siteinstitucional.metricas.dto.MetricaImpactoDTO;
import com.brasfi.siteinstitucional.metricas.entity.HistoricoMetrica;
import com.brasfi.siteinstitucional.metricas.entity.MetricaImpacto;
import com.brasfi.siteinstitucional.metricas.service.MetricaImpactoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MetricaImpactoController {

    private final MetricaImpactoService metricaService;

    // Endpoints públicos
    @GetMapping("/api/public/metricas")
    public ResponseEntity<List<MetricaImpacto>> listarMetricasAtivas() {
        return ResponseEntity.ok(metricaService.listarAtivas());
    }

    @GetMapping("/api/public/metricas/ano/{ano}")
    public ResponseEntity<List<MetricaImpacto>> listarMetricasPorAno(@PathVariable Integer ano) {
        return ResponseEntity.ok(metricaService.listarPorAno(ano));
    }

    @GetMapping("/api/public/metricas/categoria/{categoria}")
    public ResponseEntity<List<MetricaImpacto>> listarMetricasPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(metricaService.listarPorCategoria(categoria));
    }

    @GetMapping("/api/public/metricas/agrupadas/categoria")
    public ResponseEntity<Map<String, List<MetricaImpacto>>> listarMetricasAgrupadasPorCategoria(
            @RequestParam(required = false) Integer ano) {
        return ResponseEntity.ok(metricaService.listarAgrupadasPorCategoria(ano));
    }

    @GetMapping("/api/public/metricas/agrupadas/ano")
    public ResponseEntity<Map<Integer, List<MetricaImpacto>>> listarMetricasAgrupadasPorAno(
            @RequestParam(required = false) String categoria) {
        return ResponseEntity.ok(metricaService.listarAgrupadasPorAno(categoria));
    }

    @GetMapping("/api/public/metricas/anos")
    public ResponseEntity<List<Integer>> listarAnosDisponiveis() {
        return ResponseEntity.ok(metricaService.listarAnosDisponiveis());
    }

    @GetMapping("/api/public/metricas/categorias")
    public ResponseEntity<List<String>> listarCategoriasDisponiveis() {
        return ResponseEntity.ok(metricaService.listarCategoriasDisponiveis());
    }

    // Endpoints administrativos
    @GetMapping("/api/admin/metricas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<MetricaImpacto>> buscarMetricas(
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String tipoIniciativa,
            @RequestParam(required = false) Boolean ativo,
            Pageable pageable) {
        return ResponseEntity.ok(metricaService.buscarComFiltros(ano, categoria, tipoIniciativa, ativo, pageable));
    }

    @GetMapping("/api/admin/metricas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetricaImpacto> buscarMetricaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(metricaService.buscarPorId(id));
    }

    @PostMapping("/api/admin/metricas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetricaImpacto> criarMetrica(@Valid @RequestBody MetricaImpactoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(metricaService.criar(dto));
    }

    @PutMapping("/api/admin/metricas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetricaImpacto> atualizarMetrica(
            @PathVariable Long id,
            @Valid @RequestBody MetricaImpactoDTO dto) {
        return ResponseEntity.ok(metricaService.atualizar(id, dto));
    }

    @DeleteMapping("/api/admin/metricas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirMetrica(@PathVariable Long id) {
        metricaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/admin/metricas/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetricaImpacto> ativarMetrica(@PathVariable Long id) {
        return ResponseEntity.ok(metricaService.ativar(id));
    }

    @PatchMapping("/api/admin/metricas/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetricaImpacto> desativarMetrica(@PathVariable Long id) {
        return ResponseEntity.ok(metricaService.desativar(id));
    }

    @GetMapping("/api/admin/metricas/{id}/historico")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HistoricoMetrica>> buscarHistorico(
            @PathVariable Long id,
            Pageable pageable) {
        return ResponseEntity.ok(metricaService.buscarHistorico(id, pageable));
    }
}