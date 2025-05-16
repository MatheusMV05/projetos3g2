package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.PilaresDTO;
import com.brasfi.siteinstitucional.entity.Pilares;
import com.brasfi.siteinstitucional.service.PilaresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pilares")
public class PilaresController {

    @Autowired
    private PilaresService pilaresService;

    @GetMapping
    public ResponseEntity<List<Pilares>> listarPilares() {
        return ResponseEntity.ok(pilaresService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pilares> buscarPilaresPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pilaresService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Pilares> criarPilares(@Valid @RequestBody PilaresDTO pilaresDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pilaresService.salvar(pilaresDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pilares> atualizarPilares(@PathVariable Long id, @Valid @RequestBody PilaresDTO pilaresDTO) {
        return ResponseEntity.ok(pilaresService.atualizar(id, pilaresDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPilares(@PathVariable Long id) {
        pilaresService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}