package com.brasfi.siteinstitucional.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * DTO para dados de dashboard de métricas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMetricaDTO {
    private Integer totalMetricas;
    private BigDecimal mediaValor;
    private BigDecimal valorMaximo;
    private BigDecimal valorMinimo;
    private Map<String, Long> metricasPorUnidade;
    private Map<Integer, Double> evolucaoMensal;
    private List<Map<String, Object>> ultimasMetricas;
}