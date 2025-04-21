package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.PaginaDTO;
import com.brasfi.siteinstitucional.model.Pagina;
import com.brasfi.siteinstitucional.service.PaginaService;
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
    private PaginaService paginaService;

    @GetMapping
    public ResponseEntity<List<Pagina>> listarPaginas() {
        return ResponseEntity.ok(paginaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagina> buscarPaginaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(paginaService.buscarPorId(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Pagina> buscarPaginaPorSlug(@PathVariable String slug) {
        return ResponseEntity.ok(paginaService.buscarPorSlug(slug));
    }

    @PostMapping
    public ResponseEntity<Pagina> criarPagina(@Valid @RequestBody PaginaDTO paginaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paginaService.salvar(paginaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagina> atualizarPagina(@PathVariable Long id, @Valid @RequestBody PaginaDTO paginaDTO) {
        return ResponseEntity.ok(paginaService.atualizar(id, paginaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPagina(@PathVariable Long id) {
        paginaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}