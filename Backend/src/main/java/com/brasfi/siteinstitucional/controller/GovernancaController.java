package com.brasfi.siteinstitucional.controller;


import com.brasfi.siteinstitucional.dto.GovernancaDTO;
import com.brasfi.siteinstitucional.entity.Governanca;
import com.brasfi.siteinstitucional.service.GovernancaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/governancas")
public class GovernancaController {

    @Autowired
    private GovernancaService governancaService;

    @GetMapping
    public ResponseEntity<List<Governanca>> listarGovernancas(){
        return ResponseEntity.ok(governancaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Governanca> buscarGovernancaPorId(@PathVariable Long id){
        return ResponseEntity.ok(governancaService.buscarPorId(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Governanca> buscarGovernancaPorSlug(@PathVariable String slug){
        return ResponseEntity.ok(governancaService.buscarPorSlug(slug));
    }

    @PostMapping
    public ResponseEntity<Governanca> criarGovernanca(@Valid @RequestBody GovernancaDTO governancaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(governancaService.salvar(governancaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Governanca> atualizarGovernanca(@PathVariable Long id, @Valid @RequestBody GovernancaDTO governancaDTO){
        return ResponseEntity.ok(governancaService.atualizar(id, governancaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPagina(@PathVariable Long id){
        governancaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
