package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.entity.Inscricao;
import com.brasfi.siteinstitucional.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    @Autowired
    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    // ... (Endpoints existentes: inscreverEmEvento, getInscricaoById, getInscricoesByEvento,
    //      confirmarInscricao, cancelarInscricao, getAllInscricoes) ...
    @PostMapping
    public ResponseEntity<Inscricao> inscreverEmEvento(@RequestBody Map<String, String> payload) {
        Long eventoId = Long.valueOf(payload.get("eventoId"));
        String nomeCompleto = payload.get("nomeCompleto");
        String email = payload.get("email");

        Inscricao novaInscricao = inscricaoService.realizarInscricao(eventoId, nomeCompleto, email);
        return new ResponseEntity<>(novaInscricao, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> getInscricaoById(@PathVariable Long id) {
        return inscricaoService.buscarInscricaoPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mantendo este GET para listar inscrições JSON, e criando um novo para CSV
    @GetMapping(params = "eventoId")
    public ResponseEntity<List<Inscricao>> getInscricoesByEvento(@RequestParam Long eventoId) {
        List<Inscricao> inscricoes = inscricaoService.buscarInscricoesPorEvento(eventoId);
        return ResponseEntity.ok(inscricoes);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Inscricao> confirmarInscricao(@PathVariable Long id) {
        Inscricao inscricaoConfirmada = inscricaoService.confirmarInscricao(id);
        return ResponseEntity.ok(inscricaoConfirmada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarInscricao(@PathVariable Long id) {
        inscricaoService.cancelarInscricao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Inscricao>> getAllInscricoes() {
        return ResponseEntity.ok(inscricaoService.findAllInscricoes());
    }


    /**
     * Endpoint para exportar a lista de participantes de um evento específico em formato CSV.
     * Somente acessível por administradores.
     * GET /api/inscricoes/exportar/{eventoId}/csv
     */
    @GetMapping("/exportar/{eventoId}/csv")
    public ResponseEntity<String> exportarParticipantesCsv(@PathVariable Long eventoId) {
        String csvData = inscricaoService.exportarParticipantesCsv(eventoId);

        String filename = "participantes_evento_" + eventoId + "_" + java.time.LocalDate.now() + ".csv";

        // Configura os cabeçalhos para que o navegador baixe o arquivo
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8"); // Garante codificação correta
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }
}