package com.brasfi.siteinstitucional.config;

import com.brasfi.siteinstitucional.auth.entity.Perfil;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.PerfilRepository;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== INICIANDO DATALOADER - CRIANDO DADOS INICIAIS ===");

        // Criar perfis se não existirem
        Perfil perfilAdmin = createPerfilIfNotExists("ADMIN", "Administrador do sistema");
        Perfil perfilUser = createPerfilIfNotExists("USER", "Usuário comum");

        // Criar usuário admin padrão se não existir
        createDefaultAdminUser(perfilAdmin);

        log.info("=== DATALOADER CONCLUÍDO COM SUCESSO ===");
    }

    private Perfil createPerfilIfNotExists(String nome, String descricao) {
        return perfilRepository.findByNome(nome).orElseGet(() -> {
            Perfil perfil = new Perfil();
            perfil.setNome(nome);
            perfil.setDescricao(descricao);
            Perfil savedPerfil = perfilRepository.save(perfil);
            log.info("✅ Perfil '{}' criado com sucesso - ID: {}", nome, savedPerfil.getId());
            return savedPerfil;
        });
    }

    private void createDefaultAdminUser(Perfil perfilAdmin) {
        String adminEmail = "admin@brasfi.com.br";
        String adminPassword = "123456";
        String adminName = "Administrador BRASFI";

        if (!usuarioRepository.existsByEmail(adminEmail)) {
            log.info("Criando usuário administrador padrão...");

            try {
                Set<Perfil> perfis = new HashSet<>();
                perfis.add(perfilAdmin);

                Usuario adminUser = Usuario.builder()
                        .nome(adminName)
                        .email(adminEmail)
                        .senha(passwordEncoder.encode(adminPassword))
                        .perfis(perfis)
                        .ativo(true)
                        .mfaAtivado(false)
                        .build();

                Usuario savedUser = usuarioRepository.save(adminUser);

                log.info("✅ USUÁRIO ADMIN CRIADO COM SUCESSO!");
                log.info("   📧 Email: {}", savedUser.getEmail());
                log.info("   👤 Nome: {}", savedUser.getNome());
                log.info("   🆔 ID: {}", savedUser.getId());
                log.info("   🔑 Senha: {} (criptografada)", adminPassword);
                log.info("   🛡️ Perfis: {}", savedUser.getPerfis().size());
                log.info("   ✅ Ativo: {}", savedUser.isAtivo());

                // Verificar se o perfil foi associado corretamente
                savedUser.getPerfis().forEach(perfil ->
                        log.info("   📋 Perfil associado: {} (ID: {})", perfil.getNome(), perfil.getId())
                );

            } catch (Exception e) {
                log.error("❌ ERRO ao criar usuário administrador padrão: {}", e.getMessage(), e);
                throw new RuntimeException("Falha ao criar usuário administrador padrão", e);
            }
        } else {
            log.info("✅ Usuário administrador padrão já existe: {}", adminEmail);

            // Verificar se o usuário tem o perfil ADMIN
            Usuario existingAdmin = usuarioRepository.findByEmail(adminEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário admin existe mas não foi encontrado"));

            boolean hasAdminRole = existingAdmin.getPerfis().stream()
                    .anyMatch(perfil -> "ADMIN".equals(perfil.getNome()));

            if (hasAdminRole) {
                log.info("   ✅ Usuário tem perfil ADMIN corretamente configurado");
            } else {
                log.warn("   ⚠️ Usuário existe mas NÃO tem perfil ADMIN - Corrigindo...");

                Set<Perfil> perfis = new HashSet<>(existingAdmin.getPerfis());
                perfis.add(perfilAdmin);
                existingAdmin.setPerfis(perfis);
                usuarioRepository.save(existingAdmin);

                log.info("   ✅ Perfil ADMIN adicionado ao usuário existente");
            }
        }
    }
}