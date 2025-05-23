package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.HistoriaDTO;
import com.brasfi.siteinstitucional.entity.Historia;
import com.brasfi.siteinstitucional.service.HistoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historias")
public class HistoriaController {

    @Autowired
    private HistoriaService historiaService;

    @GetMapping
    public ResponseEntity<List<Historia>> listarHistorias() {
        return ResponseEntity.ok(historiaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Historia> buscarHistoriaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historiaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Historia> criarHistoria(@Valid @RequestBody HistoriaDTO historiaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historiaService.salvar(historiaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Historia> atualizarHistoria(@PathVariable Long id, @Valid @RequestBody HistoriaDTO historiaDTO) {
        return ResponseEntity.ok(historiaService.atualizar(id, historiaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirHistoria(@PathVariable Long id) {
        historiaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}