package com.brasfi.siteinstitucional.admin.notification.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacaoEvent {
    private Long usuarioId;
    private String titulo;
    private String mensagem;
    private String tipo;
    private String acao;
}