package com.brasfi.siteinstitucional.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogAuditoriaDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private String acao;
    private String entidade;
    private Long entidadeId;
    private String detalhes;
    private String ip;
    private LocalDateTime dataHora;
}