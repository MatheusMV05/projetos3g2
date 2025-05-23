package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.PaginaDTO;
import com.brasfi.siteinstitucional.entity.Pagina;
import com.brasfi.siteinstitucional.mediator.PaginaMediator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paginas")
public class PaginaController {

    @Autowired
    private PaginaMediator paginaMediator;

    @GetMapping
    public ResponseEntity<List<Pagina>> listarPaginas() {
        return ResponseEntity.ok(paginaMediator.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagina> buscarPaginaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(paginaMediator.buscarPorId(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Pagina> buscarPaginaPorSlug(@PathVariable String slug) {
        return ResponseEntity.ok(paginaMediator.buscarPorSlug(slug));
    }

    @PostMapping
    public ResponseEntity<Pagina> criarPagina(@Valid @RequestBody PaginaDTO paginaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paginaMediator.salvar(paginaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagina> atualizarPagina(@PathVariable Long id, @Valid @RequestBody PaginaDTO paginaDTO) {
        return ResponseEntity.ok(paginaMediator.atualizar(id, paginaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPagina(@PathVariable Long id) {
        paginaMediator.excluir(id);
        return ResponseEntity.noContent().build();
    }
}