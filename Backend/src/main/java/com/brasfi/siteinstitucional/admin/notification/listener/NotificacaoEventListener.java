package com.brasfi.siteinstitucional.admin.notification.listener;

import com.brasfi.siteinstitucional.admin.notification.event.NotificacaoEvent;
import com.brasfi.siteinstitucional.admin.notification.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacaoEventListener {

    private final NotificacaoService notificacaoService;

    @Async
    @EventListener
    public void handleNotificacaoEvent(NotificacaoEvent event) {
        try {
            notificacaoService.criarNotificacao(
                    event.getTitulo(),
                    event.getMensagem(),
                    event.getTipo(),
                    event.getAcao(),
                    event.getUsuarioId()
            );
            log.info("Notificação criada com sucesso para o usuário: {}", event.getUsuarioId());
        } catch (Exception e) {
            log.error("Erro ao processar evento de notificação: {}", e.getMessage(), e);
        }
    }
}