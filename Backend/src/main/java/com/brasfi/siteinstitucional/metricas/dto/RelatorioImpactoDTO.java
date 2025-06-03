package com.brasfi.siteinstitucional.metricas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioImpactoDTO {

    private String titulo;
    private String subtitulo;
    private Integer ano;
    private LocalDateTime dataGeracao;

    private List<MetricaImpactoDTO> metricas;
    private List<DepoimentoDTO> depoimentos;

    private Map<String, List<MetricaImpactoDTO>> metricasPorCategoria;
    private Map<Integer, List<MetricaImpactoDTO>> metricasPorAno;

    private String resumoExecutivo;
    private String conclusao;
}