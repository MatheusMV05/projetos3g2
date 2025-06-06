package com.brasfi.siteinstitucional.admin.notification.publisher;

import com.brasfi.siteinstitucional.admin.notification.event.NotificacaoEvent;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificacaoPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final UsuarioRepository usuarioRepository;

    public void publicarParaUsuario(Long usuarioId, String titulo, String mensagem, String tipo, String acao) {
        NotificacaoEvent event = NotificacaoEvent.builder()
                .usuarioId(usuarioId)
                .titulo(titulo)
                .mensagem(mensagem)
                .tipo(tipo)
                .acao(acao)
                .build();

        eventPublisher.publishEvent(event);
    }

    public void publicarParaAdmins(String titulo, String mensagem, String tipo, String acao) {
        List<Long> adminIds = usuarioRepository.findAdminIds();

        for (Long adminId : adminIds) {
            publicarParaUsuario(adminId, titulo, mensagem, tipo, acao);
        }
    }

    public void publicarParaTodos(String titulo, String mensagem, String tipo, String acao) {
        List<Long> usuarioIds = usuarioRepository.findAllIds();

        for (Long usuarioId : usuarioIds) {
            publicarParaUsuario(usuarioId, titulo, mensagem, tipo, acao);
        }
    }
}