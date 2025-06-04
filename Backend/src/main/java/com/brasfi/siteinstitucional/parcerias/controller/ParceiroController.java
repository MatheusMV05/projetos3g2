package com.brasfi.siteinstitucional.parcerias.controller;

import com.brasfi.siteinstitucional.parcerias.dto.ParceiroDTO;
import com.brasfi.siteinstitucional.parcerias.dto.ParceiroSummaryDTO;
import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import com.brasfi.siteinstitucional.parcerias.service.ParceiroService;
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
public class ParceiroController {

    private final ParceiroService parceiroService;

    // Endpoints Públicos
    @GetMapping("/public/parceiros")
    public ResponseEntity<Page<ParceiroSummaryDTO>> listarParceirosPublico(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) TipoParceria tipo,
            @RequestParam(required = false) String setor,
            @RequestParam(required = false) Long categoriaId,
            Pageable pageable) {
        return ResponseEntity.ok(parceiroService.listarParceirosVisiveisPaginadoComFiltros(nome, tipo, setor, categoriaId, pageable));
    }

    @GetMapping("/public/parceiros/todos") // Endpoint para listar todos visíveis sem paginação, se necessário
    public ResponseEntity<List<ParceiroSummaryDTO>> listarTodosParceirosVisiveis() {
        return ResponseEntity.ok(parceiroService.listarParceirosVisiveis());
    }


    // Endpoints Administrativos
    @GetMapping("/admin/parceiros")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ParceiroDTO>> buscarParceirosAdmin(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) TipoParceria tipo,
            @RequestParam(required = false) String setor,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Boolean visivel,
            @RequestParam(required = false) Boolean ativo,
            Pageable pageable) {
        return ResponseEntity.ok(parceiroService.buscarParceirosAdmin(nome, tipo, setor, categoriaId, visivel, ativo, pageable));
    }

    @GetMapping("/admin/parceiros/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParceiroDTO> buscarParceiroPorIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(parceiroService.buscarParceiroPorIdAdmin(id));
    }

    @PostMapping("/admin/parceiros")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParceiroDTO> criarParceiro(@Valid @RequestBody ParceiroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parceiroService.criarParceiro(dto));
    }

    @PutMapping("/admin/parceiros/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParceiroDTO> atualizarParceiro(@PathVariable Long id, @Valid @RequestBody ParceiroDTO dto) {
        return ResponseEntity.ok(parceiroService.atualizarParceiro(id, dto));
    }

    @PatchMapping("/admin/parceiros/{id}/visibilidade")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParceiroDTO> alterarVisibilidadeParceiro(@PathVariable Long id, @RequestParam boolean visivel) {
        return ResponseEntity.ok(parceiroService.alterarVisibilidade(id, visivel));
    }

    @PatchMapping("/admin/parceiros/{id}/estado-ativo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParceiroDTO> alterarEstadoAtivoParceiro(@PathVariable Long id, @RequestParam boolean ativo) {
        return ResponseEntity.ok(parceiroService.alterarEstadoAtivo(id, ativo));
    }

    @DeleteMapping("/admin/parceiros/{id}") // Soft delete
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirLogicoParceiro(@PathVariable Long id) {
        parceiroService.excluirLogicoParceiro(id);
        return ResponseEntity.noContent().build();
    }
}