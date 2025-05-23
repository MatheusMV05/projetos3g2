package com.brasfi.siteinstitucional.admin.scheduling.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.admin.scheduling.dto.PublicacaoAgendadaDTO;
import com.brasfi.siteinstitucional.admin.scheduling.entity.PublicacaoAgendada;
import com.brasfi.siteinstitucional.admin.scheduling.repository.PublicacaoAgendadaRepository;
import com.brasfi.siteinstitucional.admin.service.AuditoriaService;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PublicacaoAgendadaService {

    private final PublicacaoAgendadaRepository publicacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final AuditoriaService auditoriaService;

    // Mapa de serviços de publicação
    private final List<PublicadorConteudo> publicadores;

    // Adicionando construtor com todos os parâmetros
    @Autowired
    public PublicacaoAgendadaService(
            PublicacaoAgendadaRepository publicacaoRepository,
            UsuarioRepository usuarioRepository,
            ObjectMapper objectMapper,
            AuditoriaService auditoriaService,
            List<PublicadorConteudo> publicadores) {
        this.publicacaoRepository = publicacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
        this.auditoriaService = auditoriaService;
        this.publicadores = publicadores;
    }

    @Auditavel(acao = "CRIAR", entidade = "PUBLICACAO_AGENDADA")
    public PublicacaoAgendada agendar(PublicacaoAgendadaDTO dto) {
        // Validar se a data de publicação é futura
        if (dto.getDataPublicacao().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de publicação deve ser futura");
        }

        // Obter o usuário autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Criar a entidade de publicação agendada
        PublicacaoAgendada publicacao = PublicacaoAgendada.builder()
                .tipoConteudo(dto.getTipoConteudo())
                .conteudoId(dto.getConteudoId())
                .conteudoJson(dto.getConteudoJson())
                .dataPublicacao(dto.getDataPublicacao())
                .usuario(usuario)
                .build();

        // Salvar a publicação agendada
        return publicacaoRepository.save(publicacao);
    }

    public PublicacaoAgendada buscarPorId(Long id) {
        return publicacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicação agendada não encontrada"));
    }

    @Auditavel(acao = "ATUALIZAR", entidade = "PUBLICACAO_AGENDADA")
    public PublicacaoAgendada atualizar(Long id, PublicacaoAgendadaDTO dto) {
        PublicacaoAgendada publicacao = buscarPorId(id);

        // Verificar se já foi publicada
        if (publicacao.isPublicado()) {
            throw new IllegalStateException("Não é possível atualizar uma publicação já publicada");
        }

        // Validar se a data de publicação é futura
        if (dto.getDataPublicacao().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de publicação deve ser futura");
        }

        // Atualizar campos
        publicacao.setTipoConteudo(dto.getTipoConteudo());
        publicacao.setConteudoId(dto.getConteudoId());
        publicacao.setConteudoJson(dto.getConteudoJson());
        publicacao.setDataPublicacao(dto.getDataPublicacao());

        return publicacaoRepository.save(publicacao);
    }

    @Auditavel(acao = "EXCLUIR", entidade = "PUBLICACAO_AGENDADA")
    public void excluir(Long id) {
        PublicacaoAgendada publicacao = buscarPorId(id);

        // Verificar se já foi publicada
        if (publicacao.isPublicado()) {
            throw new IllegalStateException("Não é possível excluir uma publicação já publicada");
        }

        publicacaoRepository.delete(publicacao);
    }

    public Page<PublicacaoAgendada> listar(
            String tipoConteudo,
            Boolean publicado,
            Long usuarioId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Pageable pageable) {

        return publicacaoRepository.buscarComFiltros(
                tipoConteudo, publicado, usuarioId, dataInicio, dataFim, pageable);
    }

    @Scheduled(fixedRate = 60000) // Executa a cada minuto
    @Transactional
    public void processarPublicacoesAgendadas() {
        LocalDateTime agora = LocalDateTime.now();
        List<PublicacaoAgendada> publicacoesParaProcessar =
                publicacaoRepository.findByPublicadoFalseAndDataPublicacaoLessThanEqual(agora);

        log.info("Processando {} publicações agendadas", publicacoesParaProcessar.size());

        for (PublicacaoAgendada publicacao : publicacoesParaProcessar) {
            try {
                processarPublicacao(publicacao);

                // Marcar como publicada
                publicacao.setPublicado(true);
                publicacaoRepository.save(publicacao);

                // Registrar na auditoria
                auditoriaService.registrarAcao(
                        "PUBLICAR",
                        "PUBLICACAO_AGENDADA",
                        publicacao.getId(),
                        "Publicação processada automaticamente pelo sistema");

            } catch (Exception e) {
                log.error("Erro ao processar publicação agendada {}: {}", publicacao.getId(), e.getMessage(), e);
            }
        }
    }

    private void processarPublicacao(PublicacaoAgendada publicacao) {
        // Encontrar o publicador adequado para o tipo de conteúdo
        for (PublicadorConteudo publicador : publicadores) {
            if (publicador.suportaTipo(publicacao.getTipoConteudo())) {
                publicador.publicar(publicacao);
                return;
            }
        }

        log.warn("Nenhum publicador encontrado para o tipo {}", publicacao.getTipoConteudo());
    }
}
