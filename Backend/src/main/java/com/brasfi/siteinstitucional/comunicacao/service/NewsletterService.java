package com.brasfi.siteinstitucional.comunicacao.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.comunicacao.dto.AssinanteDTO;
import com.brasfi.siteinstitucional.comunicacao.dto.NewsletterDTO;
import com.brasfi.siteinstitucional.comunicacao.entity.Assinante;
import com.brasfi.siteinstitucional.comunicacao.entity.Newsletter;
import com.brasfi.siteinstitucional.comunicacao.repository.AssinanteRepository;
import com.brasfi.siteinstitucional.comunicacao.repository.NewsletterRepository;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsletterService {

    private final AssinanteRepository assinanteRepository;
    private final NewsletterRepository newsletterRepository;
    private final EmailService emailService;
    private final RecaptchaService recaptchaService;

    @Auditavel(acao = "INSCREVER", entidade = "ASSINANTE_NEWSLETTER")
    public Assinante inscrever(AssinanteDTO assinanteDTO) {
        // Validar reCAPTCHA
        if (!recaptchaService.validarToken(assinanteDTO.getRecaptchaToken())) {
            throw new IllegalArgumentException("Verificação de segurança falhou. Por favor, tente novamente.");
        }

        // Verificar se já existe
        if (assinanteRepository.existsByEmail(assinanteDTO.getEmail())) {
            // Reativar se estiver inativo
            Assinante assinanteExistente = assinanteRepository.findByEmail(assinanteDTO.getEmail())
                    .orElseThrow();

            if (!assinanteExistente.isAtivo()) {
                assinanteExistente.setAtivo(true);
                assinanteExistente.setDataDesativacao(null);
                return assinanteRepository.save(assinanteExistente);
            }

            return assinanteExistente;
        }

        // Criar novo assinante
        Assinante assinante = Assinante.builder()
                .email(assinanteDTO.getEmail())
                .nome(assinanteDTO.getNome())
                .ativo(true)
                .build();

        Assinante assinanteSalvo = assinanteRepository.save(assinante);

        // Enviar email de confirmação
        try {
            Map<String, Object> templateVars = new HashMap<>();
            templateVars.put("nome", assinante.getNome() != null ? assinante.getNome() : "");

            emailService.enviarEmailHtml(
                    assinante.getEmail(),
                    "Confirmação de inscrição na newsletter BRASFI",
                    "confirmacao-newsletter",
                    templateVars
            );
        } catch (Exception e) {
            log.error("Erro ao enviar email de confirmação: {}", e.getMessage(), e);
        }

        return assinanteSalvo;
    }

    @Auditavel(acao = "CANCELAR", entidade = "ASSINANTE_NEWSLETTER")
    public void cancelarInscricao(String email) {
        Assinante assinante = assinanteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado na lista de assinantes"));

        assinante.setAtivo(false);
        assinante.setDataDesativacao(LocalDateTime.now());
        assinanteRepository.save(assinante);
    }

    public Page<Assinante> listarAssinantes(Boolean ativo, Pageable pageable) {
        if (ativo != null) {
            return assinanteRepository.findByAtivo(ativo, pageable);
        }
        return assinanteRepository.findAll(pageable);
    }

    @Auditavel(acao = "CRIAR", entidade = "NEWSLETTER")
    public Newsletter criarNewsletter(NewsletterDTO newsletterDTO, Long usuarioId) {
        Newsletter newsletter = Newsletter.builder()
                .assunto(newsletterDTO.getAssunto())
                .conteudo(newsletterDTO.getConteudo())
                .enviada(false)
                .usuarioId(usuarioId)
                .build();

        Newsletter newsletterSalva = newsletterRepository.save(newsletter);

        // Se solicitado envio imediato
        if (newsletterDTO.getEnviarImediatamente() != null && newsletterDTO.getEnviarImediatamente()) {
            enviarNewsletter(newsletterSalva.getId());
        }

        return newsletterSalva;
    }

    @Auditavel(acao = "ENVIAR", entidade = "NEWSLETTER")
    public Newsletter enviarNewsletter(Long id) {
        Newsletter newsletter = newsletterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Newsletter não encontrada"));

        if (newsletter.getEnviada()) {
            throw new IllegalStateException("Esta newsletter já foi enviada");
        }

        // Obter todos assinantes ativos
        List<Assinante> assinantesAtivos = assinanteRepository.findByAtivo(true, Pageable.unpaged()).getContent();

        if (assinantesAtivos.isEmpty()) {
            throw new IllegalStateException("Não há assinantes ativos para enviar a newsletter");
        }

        List<String> emails = assinantesAtivos.stream()
                .map(Assinante::getEmail)
                .collect(Collectors.toList());

        try {
            Map<String, Object> templateVars = new HashMap<>();
            templateVars.put("conteudo", newsletter.getConteudo());

            emailService.enviarEmailEmMassa(
                    emails,
                    newsletter.getAssunto(),
                    "template-newsletter",
                    templateVars
            );

            // Atualizar status
            newsletter.setEnviada(true);
            newsletter.setDataEnvio(LocalDateTime.now());
            newsletter.setQuantidadeDestinatarios(emails.size());

            return newsletterRepository.save(newsletter);

        } catch (Exception e) {
            log.error("Erro ao enviar newsletter: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao enviar newsletter: " + e.getMessage());
        }
    }

    public Page<Newsletter> listarNewsletters(Boolean enviada, Long usuarioId, Pageable pageable) {
        if (enviada != null) {
            return newsletterRepository.findByEnviada(enviada, pageable);
        } else if (usuarioId != null) {
            return newsletterRepository.findByUsuarioId(usuarioId, pageable);
        }
        return newsletterRepository.findAll(pageable);
    }

    public Newsletter buscarNewsletter(Long id) {
        return newsletterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Newsletter não encontrada"));
    }
}