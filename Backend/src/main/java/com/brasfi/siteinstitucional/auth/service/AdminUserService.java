package com.brasfi.siteinstitucional.auth.service;

import com.brasfi.siteinstitucional.auth.dto.AdminUserCreateRequest;
import com.brasfi.siteinstitucional.auth.dto.UsuarioDTO;
import com.brasfi.siteinstitucional.auth.dto.UsuarioUpdateDTO;
import com.brasfi.siteinstitucional.auth.entity.Perfil;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.PerfilRepository;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (usuarioRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new IllegalArgumentException("Já existe um usuário com este email: " + request.getEmail());
        }
        Perfil adminRole = perfilRepository.findByNome("ADMIN")
                .orElseThrow(() -> new RuntimeException("Perfil ADMIN não encontrado."));
        Set<Perfil> perfis = new HashSet<>();
        perfis.add(adminRole);
        Usuario usuario = Usuario.builder()
                .nome(request.getNome().trim())
                .email(request.getEmail().trim().toLowerCase())
                .senha(passwordEncoder.encode(request.getSenha()))
                .perfis(perfis)
                .ativo(true)
                .mfaAtivado(false)
                .build();
        usuarioRepository.save(usuario);
        log.info("Usuário admin criado com sucesso. ID: {}", usuario.getId());
    }

    private UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .ativo(usuario.isAtivo())
                .ultimoLogin(usuario.getUltimoLogin())
                .dataCriacao(usuario.getDataCriacao())
                .perfis(usuario.getPerfis().stream().map(Perfil::getNome).collect(Collectors.toSet()))
                .build();
    }

    public List<UsuarioDTO> findAllUsers() {
        log.info("Buscando todos os usuários");
        return usuarioRepository.findAll().stream()
                .map(this::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Tentando deletar usuário com ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
        log.info("Usuário com ID {} deletado com sucesso.", id);
    }
}