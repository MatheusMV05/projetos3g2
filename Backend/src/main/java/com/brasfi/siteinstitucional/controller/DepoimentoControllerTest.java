package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.entity.Depoimento;
import com.brasfi.siteinstitucional.service.DepoimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/depoimentos")
public class DepoimentoControllerTest{

    @Autowired
    private DepoimentoService depoimentoService;

    @GetMapping
    public ResponseEntity<?> listarDepoimentos(
            @RequestParam(required = false) String nome,
            Pageable pageable) {

        boolean semFiltro = (nome == null || nome.isEmpty()) && pageable.isUnpaged();

        if (semFiltro) {
            return ResponseEntity.ok(depoimentoService.listarTodosSemFiltro());
        } else {
            return ResponseEntity.ok(depoimentoService.listarTodos(nome, pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depoimento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(depoimentoService.buscarPorId(id));
    }
}
