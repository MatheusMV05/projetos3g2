package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.DashboardMetricaDTO;
import com.brasfi.siteinstitucional.dto.MetricaFiltroDTO;
import com.brasfi.siteinstitucional.service.DashboardMetricaService;
import com.brasfi.siteinstitucional.service.RelatorioPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/metricas")
@RequiredArgsConstructor
public class MetricaController {

    private final DashboardMetricaService dashboardMetricaService;
    private final RelatorioPdfService relatorioPdfService;

    /**
     * Endpoint para obter dados do dashboard de métricas
     *
     * @return ResponseEntity com DashboardMetricaDTO
     */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardMetricaDTO> getDashboardMetricas() {
        DashboardMetricaDTO dashboard = dashboardMetricaService.gerarDadosDashboard();
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Endpoint para obter estatísticas por unidade de medida
     *
     * @return ResponseEntity com lista de estatísticas
     */
    @GetMapping("/estatisticas/unidade")
    public ResponseEntity<List<Map<String, Object>>> getEstatisticasPorUnidade() {
        List<Map<String, Object>> estatisticas = dashboardMetricaService.obterEstatisticasPorUnidade();
        return ResponseEntity.ok(estatisticas);
    }

    /**
     * Endpoint para download de relatório em PDF
     *
     * @param filtro filtros para as métricas
     * @return ResponseEntity com o arquivo PDF para download
     */
    @PostMapping("/relatorio/pdf")
    public ResponseEntity<InputStreamResource> downloadRelatorioPdf(@RequestBody MetricaFiltroDTO filtro) {
        ByteArrayInputStream pdfStream = relatorioPdfService.gerarRelatorioPdf(filtro);

        String dataAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "relatorio_metricas_" + dataAtual + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}