package com.brasfi.siteinstitucional.admin.notification.service;

import com.brasfi.siteinstitucional.admin.notification.dto.NotificacaoDTO;
import com.brasfi.siteinstitucional.admin.notification.entity.Notificacao;
import com.brasfi.siteinstitucional.admin.notification.repository.NotificacaoRepository;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<NotificacaoDTO> listarNotificacoes(
            Long usuarioId,
            Boolean lida,
            String tipo,
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable pageable) {

        Page<Notificacao> notificacoes = notificacaoRepository.buscarComFiltros(usuarioId, lida, tipo, inicio, fim, pageable);
        return notificacoes.map(this::converterParaDTO);
    }

    public List<NotificacaoDTO> listarUltimasNaoLidas(Long usuarioId) {
        List<Notificacao> notificacoes = notificacaoRepository.findTop5ByUsuarioIdAndLidaOrderByDataCriacaoDesc(usuarioId, false);
        return notificacoes.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public long contarNaoLidas(Long usuarioId) {
        return notificacaoRepository.countNaoLidasByUsuarioId(usuarioId);
    }

    @Transactional
    public NotificacaoDTO marcarComoLida(Long id, Long usuarioId) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));

        // Verificar se a notificação pertence ao usuário
        if (!notificacao.getUsuario().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("Esta notificação não pertence ao usuário informado");
        }

        notificacao.setLida(true);
        notificacao.setDataLeitura(LocalDateTime.now());

        notificacaoRepository.save(notificacao);

        return converterParaDTO(notificacao);
    }

    @Transactional
    public void marcarTodasComoLidas(Long usuarioId) {
        Page<Notificacao> notificacoes = notificacaoRepository.findByUsuarioIdAndLida(usuarioId, false, Pageable.unpaged());

        for (Notificacao notificacao : notificacoes) {
            notificacao.setLida(true);
            notificacao.setDataLeitura(LocalDateTime.now());
            notificacaoRepository.save(notificacao);
        }
    }

    @Transactional
    public NotificacaoDTO criarNotificacao(
            String titulo,
            String mensagem,
            String tipo,
            String acao,
            Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Notificacao notificacao = Notificacao.builder()
                .titulo(titulo)
                .mensagem(mensagem)
                .tipo(tipo)
                .acao(acao)
                .usuario(usuario)
                .lida(false)
                .build();

        notificacaoRepository.save(notificacao);

        return converterParaDTO(notificacao);
    }

    @Transactional
    public void excluirNotificacao(Long id, Long usuarioId) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));

        // Verificar se a notificação pertence ao usuário
        if (!notificacao.getUsuario().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("Esta notificação não pertence ao usuário informado");
        }

        notificacaoRepository.delete(notificacao);
    }

    private NotificacaoDTO converterParaDTO(Notificacao notificacao) {
        return NotificacaoDTO.builder()
                .id(notificacao.getId())
                .titulo(notificacao.getTitulo())
                .mensagem(notificacao.getMensagem())
                .tipo(notificacao.getTipo())
                .acao(notificacao.getAcao())
                .lida(notificacao.isLida())
                .usuarioId(notificacao.getUsuario().getId())
                .usuarioNome(notificacao.getUsuario().getNome())
                .dataCriacao(notificacao.getDataCriacao())
                .dataLeitura(notificacao.getDataLeitura())
                .build();
    }
}