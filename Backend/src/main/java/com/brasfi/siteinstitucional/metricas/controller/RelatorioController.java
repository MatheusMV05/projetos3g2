package com.brasfi.siteinstitucional.metricas.controller;

import com.brasfi.siteinstitucional.metricas.service.RelatorioPDFService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/admin/relatorios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RelatorioController {

    private final RelatorioPDFService relatorioPDFService;

    @GetMapping("/impacto/pdf")
    public ResponseEntity<byte[]> gerarRelatorioImpactoPDF(
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String categoria,
            @RequestParam(defaultValue = "true") boolean incluirDepoimentos) {

        byte[] pdf = relatorioPDFService.gerarRelatorioImpacto(ano, categoria, incluirDepoimentos);

        String nomeArquivo = String.format("relatorio_impacto_brasfi_%s.pdf",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", nomeArquivo);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}