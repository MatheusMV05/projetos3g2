package com.brasfi.siteinstitucional.i18n.controller;

import com.brasfi.siteinstitucional.i18n.dto.TraducaoDTO;
import com.brasfi.siteinstitucional.i18n.service.TraducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TraducaoController {

    private final TraducaoService traducaoService;

    // Endpoint público para obter todas as traduções de um idioma (para o frontend carregar)
    @GetMapping("/public/i18n/{idioma}")
    public ResponseEntity<Map<String, String>> getTraducoesPorIdioma(@PathVariable String idioma) {
        return ResponseEntity.ok(traducaoService.getTraducoesPorIdioma(idioma));
    }

    // Endpoint público para obter traduções de um idioma com um prefixo de chave
    @GetMapping("/public/i18n/{idioma}/prefixo/{prefixo}")
    public ResponseEntity<Map<String, String>> getTraducoesPorIdiomaEPrefixo(
            @PathVariable String idioma, @PathVariable String prefixo) {
        return ResponseEntity.ok(traducaoService.getTraducoesPorIdiomaEPrefixo(idioma, prefixo));
    }

    // Endpoints Administrativos para gerenciar traduções
    @GetMapping("/admin/i18n/traducoes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TraducaoDTO>> listarTraducoesAdmin(Pageable pageable) {
        return ResponseEntity.ok(traducaoService.listarTraducoesAdmin(pageable));
    }

    @PostMapping("/admin/i18n/traducoes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TraducaoDTO> salvarTraducao(@Valid @RequestBody TraducaoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(traducaoService.salvarTraducao(dto));
    }

    @PostMapping("/admin/i18n/traducoes/lote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TraducaoDTO>> salvarTraducoesEmLote(@Valid @RequestBody List<TraducaoDTO> dtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(traducaoService.salvarTraducoesEmLote(dtos));
    }

    @GetMapping("/admin/i18n/traducoes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TraducaoDTO> buscarTraducaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(traducaoService.buscarTraducaoPorId(id));
    }

    @DeleteMapping("/admin/i18n/traducoes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirTraducao(@PathVariable Long id) {
        traducaoService.excluirTraducao(id);
        return ResponseEntity.noContent().build();
    }
}