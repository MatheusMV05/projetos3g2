package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.PaginaDTO;
import com.brasfi.siteinstitucional.entity.Pagina;
import com.brasfi.siteinstitucional.mediator.PaginaMediator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paginas") // Considerar /api/public/paginas para acesso público
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

    // Novo endpoint de busca
    @GetMapping("/search")
    public ResponseEntity<Page<Pagina>> searchPaginas(
            @RequestParam String q,
            Pageable pageable) {
        return ResponseEntity.ok(paginaMediator.searchPaginasByTerm(q, pageable));
    }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')") // Descomentar se for endpoint administrativo
    public ResponseEntity<Pagina> criarPagina(@Valid @RequestBody PaginaDTO paginaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paginaMediator.salvar(paginaDTO));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") // Descomentar se for endpoint administrativo
    public ResponseEntity<Pagina> atualizarPagina(@PathVariable Long id, @Valid @RequestBody PaginaDTO paginaDTO) {
        return ResponseEntity.ok(paginaMediator.atualizar(id, paginaDTO));
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") // Descomentar se for endpoint administrativo
    public ResponseEntity<Void> excluirPagina(@PathVariable Long id) {
        paginaMediator.excluir(id);
        return ResponseEntity.noContent().build();
    }
}