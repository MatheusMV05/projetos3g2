package com.brasfi.siteinstitucional.admin.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoAnalyticsDTO {
    private String tipo;       // PAGEVIEW, EVENT, etc.
    private String categoria;  // Categoria do evento
    private String acao;       // Ação realizada
    private String rotulo;     // Rótulo opcional
    private Integer valor;     // Valor numérico opcional
    private String paginaReferencia;  // De onde o usuário veio
    private String paginaAtual;       // Página atual
}