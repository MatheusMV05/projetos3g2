package com.brasfi.siteinstitucional.parcerias.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.admin.notification.publisher.NotificacaoPublisher;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import com.brasfi.siteinstitucional.comunicacao.service.EmailService;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.parcerias.dto.SolicitacaoParceriaCreateDTO;
import com.brasfi.siteinstitucional.parcerias.dto.SolicitacaoParceriaDTO;
import com.brasfi.siteinstitucional.parcerias.dto.SolicitacaoParceriaUpdateStatusDTO;
import com.brasfi.siteinstitucional.parcerias.entity.SolicitacaoParceria;
import com.brasfi.siteinstitucional.parcerias.entity.StatusSolicitacao;
import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import com.brasfi.siteinstitucional.parcerias.repository.SolicitacaoParceriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitacaoParceriaService {

    private final SolicitacaoParceriaRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final NotificacaoPublisher notificacaoPublisher;

    @Value("${brasfi.admin.email:admin@brasfi.com.br}") // Configure este email no application.properties
    private String emailAdminBrasfi;

    @Transactional
    @Auditavel(acao = "CRIAR", entidade = "SOLICITACAO_PARCERIA")
    public SolicitacaoParceriaDTO criarSolicitacao(SolicitacaoParceriaCreateDTO dto) {


        SolicitacaoParceria solicitacao = SolicitacaoParceria.builder()
                .nomeOrganizacao(dto.getNomeOrganizacao())
                .nomeContato(dto.getNomeContato())
                .emailContato(dto.getEmailContato())
                .telefoneContato(dto.getTelefoneContato())
                .tipoSolicitacao(dto.getTipoSolicitacao())
                .mensagemProposta(dto.getMensagemProposta())
                .status(StatusSolicitacao.PENDENTE)
                .build();

        SolicitacaoParceria salva = solicitacaoRepository.save(solicitacao);

        // Notificar Admin
        notificarAdminNovaSolicitacao(salva);

        // Enviar email de confirmação para o solicitante
        enviarEmailConfirmacaoSolicitante(salva);

        return convertToDTO(salva);
    }

    private void notificarAdminNovaSolicitacao(SolicitacaoParceria solicitacao) {
        String tituloNotificacao = "Nova Solicitação de Parceria Recebida";
        String mensagemNotificacao = String.format(
                "Uma nova solicitação de parceria foi recebida de %s (%s).\nTipo: %s.\nEmail Contato: %s",
                solicitacao.getNomeOrganizacao(),
                solicitacao.getNomeContato(),
                solicitacao.getTipoSolicitacao().getDescricao(),
                solicitacao.getEmailContato()
        );
        String acaoNotificacao = "/admin/solicitacoes-parceria/" + solicitacao.getId();

        // Notificação no sistema para administradores
        notificacaoPublisher.publicarParaAdmins(tituloNotificacao, mensagemNotificacao, "INFO", acaoNotificacao);

        // Notificação por email para o administrador
        try {
            emailService.enviarEmailSimples(
                    emailAdminBrasfi,
                    tituloNotificacao,
                    mensagemNotificacao + "\n\nDetalhes da proposta:\n" + solicitacao.getMensagemProposta()
            );
        } catch (Exception e) {
            log.error("Erro ao enviar email de notificação de nova solicitação de parceria para admin: {}", e.getMessage(), e);
        }
    }

    private void enviarEmailConfirmacaoSolicitante(SolicitacaoParceria solicitacao) {
        try {
            Map<String, Object> variaveis = new HashMap<>();
            variaveis.put("nomeContato", solicitacao.getNomeContato());
            variaveis.put("nomeOrganizacao", solicitacao.getNomeOrganizacao());
            variaveis.put("tipoSolicitacao", solicitacao.getTipoSolicitacao().getDescricao());

            // Idealmente, criar um template HTML específico para isso (ex: "confirmacao-solicitacao-parceria.html")
            // Por simplicidade, usando um texto genérico aqui, mas o ideal é um template.
            String conteudoEmail = String.format(
                    "Olá %s,\n\nSua solicitação de %s para a organização %s foi recebida com sucesso pela BRASFI.\n" +
                            "Entraremos em contato em breve para dar seguimento.\n\nAtenciosamente,\nEquipe BRASFI",
                    solicitacao.getNomeContato(),
                    solicitacao.getTipoSolicitacao().getDescricao(),
                    solicitacao.getNomeOrganizacao()
            );

            emailService.enviarEmailSimples(
                    solicitacao.getEmailContato(),
                    "Confirmação de Solicitação de Parceria - BRASFI",
                    conteudoEmail // Substituir por templateEngine.process("template-confirmacao-solicitacao", context)
            );
            log.info("Email de confirmação enviado para {}", solicitacao.getEmailContato());
        } catch (Exception e) {
            log.error("Erro ao enviar email de confirmação para o solicitante {}: {}", solicitacao.getEmailContato(), e.getMessage(), e);
        }
    }


    public Page<SolicitacaoParceriaDTO> listarSolicitacoes(
            StatusSolicitacao status, TipoParceria tipo, LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return solicitacaoRepository.buscarComFiltros(status, tipo, inicio, fim, pageable)
                .map(this::convertToDTO);
    }

    public SolicitacaoParceriaDTO buscarPorId(Long id) {
        return solicitacaoRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitação não encontrada com id: " + id));
    }

    @Transactional
    @Auditavel(acao = "ATUALIZAR_STATUS", entidade = "SOLICITACAO_PARCERIA")
    public SolicitacaoParceriaDTO atualizarStatusSolicitacao(Long id, SolicitacaoParceriaUpdateStatusDTO dto) {
        SolicitacaoParceria solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitação não encontrada com id: " + id));

        Usuario admin = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        solicitacao.setStatus(dto.getStatus());
        solicitacao.setObservacoesAdmin(dto.getObservacoesAdmin());
        solicitacao.setAdminResponsavel(admin);
        solicitacao.setDataUltimaAtualizacaoStatus(LocalDateTime.now());

        SolicitacaoParceria atualizada = solicitacaoRepository.save(solicitacao);

        // Notificar o solicitante sobre a mudança de status (opcional, mas recomendado)
        notificarSolicitanteMudancaStatus(atualizada);

        return convertToDTO(atualizada);
    }

    private void notificarSolicitanteMudancaStatus(SolicitacaoParceria solicitacao) {
        try {
            String assunto = "Atualização sobre sua Solicitação de Parceria - BRASFI";
            String statusDescricao = solicitacao.getStatus().name().toLowerCase().replace("_", " ");
            String mensagem = String.format(
                    "Olá %s,\n\nSua solicitação de %s para a organização %s foi atualizada para: %s.\n",
                    solicitacao.getNomeContato(),
                    solicitacao.getTipoSolicitacao().getDescricao(),
                    solicitacao.getNomeOrganizacao(),
                    statusDescricao
            );

            if (solicitacao.getObservacoesAdmin() != null && !solicitacao.getObservacoesAdmin().isEmpty()) {
                mensagem += "\nObservações: " + solicitacao.getObservacoesAdmin() + "\n";
            }

            mensagem += "\nAtenciosamente,\nEquipe BRASFI";

            emailService.enviarEmailSimples(solicitacao.getEmailContato(), assunto, mensagem);
            log.info("Email de atualização de status enviado para {}", solicitacao.getEmailContato());
        } catch (Exception e) {
            log.error("Erro ao enviar email de atualização de status para {}: {}", solicitacao.getEmailContato(), e.getMessage(), e);
        }
    }


    private SolicitacaoParceriaDTO convertToDTO(SolicitacaoParceria s) {
        return SolicitacaoParceriaDTO.builder()
                .id(s.getId())
                .nomeOrganizacao(s.getNomeOrganizacao())
                .nomeContato(s.getNomeContato())
                .emailContato(s.getEmailContato())
                .telefoneContato(s.getTelefoneContato())
                .tipoSolicitacao(s.getTipoSolicitacao())
                .mensagemProposta(s.getMensagemProposta())
                .status(s.getStatus())
                .dataSolicitacao(s.getDataSolicitacao())
                .dataUltimaAtualizacaoStatus(s.getDataUltimaAtualizacaoStatus())
                .adminResponsavelId(s.getAdminResponsavel() != null ? s.getAdminResponsavel().getId() : null)
                .adminResponsavelNome(s.getAdminResponsavel() != null ? s.getAdminResponsavel().getNome() : null)
                .observacoesAdmin(s.getObservacoesAdmin())
                .build();
    }
}