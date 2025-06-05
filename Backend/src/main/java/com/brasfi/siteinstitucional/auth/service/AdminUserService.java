package com.brasfi.siteinstitucional.auth.service;

import com.brasfi.siteinstitucional.auth.dto.AdminUserCreateRequest;
import com.brasfi.siteinstitucional.auth.entity.Perfil;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.PerfilRepository;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdminUser(AdminUserCreateRequest request) {
        log.info("Iniciando criação de usuário admin");
        log.info("Request: {}", request);

        try {
            // Validações adicionais
            if (request.getNome() == null || request.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome não pode estar vazio");
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email não pode estar vazio");
            }

            if (request.getSenha() == null || request.getSenha().trim().isEmpty()) {
                throw new IllegalArgumentException("Senha não pode estar vazia");
            }

            String email = request.getEmail().trim().toLowerCase();
            log.info("Verificando se email já existe: {}", email);

            // Verificar se o email já existe
            if (usuarioRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Já existe um usuário com este email: " + email);
            }

            // Buscar o perfil ADMIN
            log.info("Buscando perfil ADMIN...");
            Perfil adminRole = perfilRepository.findByNome("ADMIN")
                    .orElseThrow(() -> {
                        log.error("Perfil ADMIN não encontrado no banco de dados");
                        return new RuntimeException("Perfil ADMIN não encontrado. Verifique se os dados iniciais foram carregados.");
                    });

            log.info("Perfil ADMIN encontrado: ID={}, Nome={}", adminRole.getId(), adminRole.getNome());

            // Criar conjunto de perfis
            Set<Perfil> perfis = new HashSet<>();
            perfis.add(adminRole);

            // Criar usuário
            log.info("Criando usuário com email: {}", email);
            Usuario usuario = Usuario.builder()
                    .nome(request.getNome().trim())
                    .email(email)
                    .senha(passwordEncoder.encode(request.getSenha()))
                    .perfis(perfis)
                    .ativo(true)
                    .mfaAtivado(false) // MFA desabilitado por padrão
                    .build();

            // Salvar usuário
            log.info("Salvando usuário no banco de dados...");
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            log.info("Usuário criado com sucesso. ID: {}, Email: {}", usuarioSalvo.getId(), usuarioSalvo.getEmail());

            // Processar foto se fornecida
            if (request.temFoto()) {
                log.info("Foto detectada: Tamanho={} bytes, Tipo={}",
                        request.getTamanhoFoto(), request.getTipoFoto());
                // TODO: Implementar upload de foto
                log.warn("Upload de foto ainda não implementado");
            } else {
                log.info("Nenhuma foto fornecida");
            }

        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação: {}", e.getMessage());
            throw e; // Re-throw para manter o tipo de exceção
        } catch (Exception e) {
            log.error("Erro inesperado ao criar usuário: {}", e.getMessage(), e);
            throw new RuntimeException("Erro interno ao criar usuário: " + e.getMessage(), e);
        }
    }
}