package com.brasfi.siteinstitucional.admin.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticasDTO {
    private List<DadosAgrupadosDTO> paginasMaisVisitadas;
    private List<DadosAgrupadosDTO> referenciasPopulares;
    private Long usuariosUnicos;
    private Long totalEventos;
    private LocalDateTime periodoInicio;
    private LocalDateTime periodoFim;
}