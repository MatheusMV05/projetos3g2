package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.MetricaDTO;
import com.brasfi.siteinstitucional.entity.Metrica;
import com.brasfi.siteinstitucional.service.MetricaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metricas")
public class MetricaController {

    @Autowired
    private MetricaService metricaService;

    @GetMapping
    public ResponseEntity<?> listarMetricas(
            @RequestParam(required = false) String nome,
            Pageable pageable) {

        boolean semFiltro = (nome == null || nome.isEmpty()) && pageable.isUnpaged();

        if (semFiltro) {
            return ResponseEntity.ok(metricaService.listarTodasSemFiltro());
        } else {
            return ResponseEntity.ok(metricaService.listarTodas(nome, pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Metrica> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(metricaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Metrica> criar(@Valid @RequestBody MetricaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(metricaService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Metrica> atualizar(@PathVariable Long id, @Valid @RequestBody MetricaDTO dto) {
        return ResponseEntity.ok(metricaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        metricaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
