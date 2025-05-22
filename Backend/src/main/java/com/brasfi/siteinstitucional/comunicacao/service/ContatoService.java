package com.brasfi.siteinstitucional.comunicacao.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.comunicacao.dto.MensagemDTO;
import com.brasfi.siteinstitucional.comunicacao.dto.RespostaContatoDTO;
import com.brasfi.siteinstitucional.comunicacao.entity.Mensagem;
import com.brasfi.siteinstitucional.comunicacao.repository.MensagemRepository;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContatoService {

    private final MensagemRepository mensagemRepository;
    private final EmailService emailService;
    private final RecaptchaService recaptchaService;

    @Auditavel(acao = "CRIAR", entidade = "MENSAGEM_CONTATO")
    public Mensagem registrarMensagem(MensagemDTO mensagemDTO) {
        // Validar reCAPTCHA
        if (!recaptchaService.validarToken(mensagemDTO.getRecaptchaToken())) {
            throw new IllegalArgumentException("Verificação de segurança falhou. Por favor, tente novamente.");
        }

        Mensagem mensagem = Mensagem.builder()
                .nome(mensagemDTO.getNome())
                .email(mensagemDTO.getEmail())
                .assunto(mensagemDTO.getAssunto())
                .mensagem(mensagemDTO.getMensagem())
                .build();

        // Notificar administradores (poderia ser implementado como um EventListener)
        try {
            emailService.enviarEmailSimples(
                    "admin@brasfi.com.br",
                    "Nova mensagem de contato: " + mensagemDTO.getAssunto(),
                    "Remetente: " + mensagemDTO.getNome() + " (" + mensagemDTO.getEmail() + ")\n\n" +
                            "Mensagem: " + mensagemDTO.getMensagem()
            );
        } catch (Exception e) {
            log.error("Erro ao enviar notificação de nova mensagem: {}", e.getMessage(), e);
        }

        return mensagemRepository.save(mensagem);
    }

    public Page<Mensagem> listarMensagens(
            Boolean lida,
            Boolean respondida,
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable pageable) {

        return mensagemRepository.buscarComFiltros(lida, respondida, inicio, fim, pageable);
    }

    public Mensagem buscarPorId(Long id) {
        return mensagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensagem não encontrada"));
    }

    @Auditavel(acao = "MARCAR_LIDA", entidade = "MENSAGEM_CONTATO")
    public Mensagem marcarComoLida(Long id) {
        Mensagem mensagem = buscarPorId(id);
        mensagem.setLida(true);
        return mensagemRepository.save(mensagem);
    }

    @Auditavel(acao = "RESPONDER", entidade = "MENSAGEM_CONTATO")
    public Mensagem responderMensagem(Long id, RespostaContatoDTO respostaDTO) {
        Mensagem mensagem = buscarPorId(id);

        // Obter usuário logado
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        mensagem.setRespondida(true);
        mensagem.setResposta(respostaDTO.getResposta());
        mensagem.setUsuarioRespostaId(usuario.getId());
        mensagem.setDataResposta(LocalDateTime.now());

        // Enviar resposta por email
        try {
            Map<String, Object> templateVars = new HashMap<>();
            templateVars.put("nome", mensagem.getNome());
            templateVars.put("mensagemOriginal", mensagem.getMensagem());
            templateVars.put("resposta", respostaDTO.getResposta());

            emailService.enviarEmailHtml(
                    mensagem.getEmail(),
                    "Resposta ao seu contato: " + mensagem.getAssunto(),
                    "resposta-contato",
                    templateVars
            );
        } catch (Exception e) {
            log.error("Erro ao enviar resposta por email: {}", e.getMessage(), e);
            // Não bloquear a operação se o email falhar
        }

        return mensagemRepository.save(mensagem);
    }
}