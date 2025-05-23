package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.SobreDTO;
import com.brasfi.siteinstitucional.entity.Sobre;
import com.brasfi.siteinstitucional.service.SobreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sobre")
public class SobreController {

    @Autowired
    private SobreService sobreService;

    @GetMapping
    public ResponseEntity<List<Sobre>> listarSobre() {
        return ResponseEntity.ok(sobreService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sobre> buscarSobrePorId(@PathVariable Long id) {
        return ResponseEntity.ok(sobreService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Sobre> criarSobre(@Valid @RequestBody SobreDTO sobreDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sobreService.salvar(sobreDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sobre> atualizarSobre(@PathVariable Long id, @Valid @RequestBody SobreDTO sobreDTO) {
        return ResponseEntity.ok(sobreService.atualizar(id, sobreDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirSobre(@PathVariable Long id) {
        sobreService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}