package com.brasfi.siteinstitucional.admin.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private String tipo;
    private String acao;
    private boolean lida;
    private Long usuarioId;
    private String usuarioNome;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataLeitura;
}