package com.brasfi.siteinstitucional.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para filtrar métricas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetricaFiltroDTO {
    private String nome;
    private String descricao;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;
    private String unidade;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String ordenacao;
}