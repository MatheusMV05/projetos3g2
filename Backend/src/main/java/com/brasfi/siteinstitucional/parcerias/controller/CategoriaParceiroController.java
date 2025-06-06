package com.brasfi.siteinstitucional.parcerias.controller;

import com.brasfi.siteinstitucional.parcerias.dto.CategoriaParceiroDTO;
import com.brasfi.siteinstitucional.parcerias.entity.CategoriaParceiro;
import com.brasfi.siteinstitucional.parcerias.service.CategoriaParceiroService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoriaParceiroController {

    private final CategoriaParceiroService categoriaService;

    // Endpoints Públicos
    @GetMapping("/public/parcerias/categorias")
    public ResponseEntity<List<CategoriaParceiro>> listarCategoriasPublico() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    // Endpoints Administrativos
    @GetMapping("/admin/parcerias/categorias")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CategoriaParceiro>> listarCategoriasAdmin(Pageable pageable) {
        return ResponseEntity.ok(categoriaService.listarPaginado(pageable));
    }

    @PostMapping("/admin/parcerias/categorias")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaParceiro> criarCategoria(@Valid @RequestBody CategoriaParceiroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criar(dto));
    }

    @GetMapping("/admin/parcerias/categorias/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaParceiro> buscarCategoriaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PutMapping("/admin/parcerias/categorias/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaParceiro> atualizarCategoria(
            @PathVariable Long id, @Valid @RequestBody CategoriaParceiroDTO dto) {
        return ResponseEntity.ok(categoriaService.atualizar(id, dto));
    }

    @DeleteMapping("/admin/parcerias/categorias/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}