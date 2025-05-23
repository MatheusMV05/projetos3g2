package com.brasfi.siteinstitucional.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO para estruturar dados do relatório PDF
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioMetricaDTO {
    private String titulo;
    private LocalDateTime dataGeracao;
    private String filtrosAplicados;
    private Integer totalMetricas;
    private List<Map<String, Object>> metricasData;
}