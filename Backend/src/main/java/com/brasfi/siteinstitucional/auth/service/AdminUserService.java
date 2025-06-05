// src/main/java/com/brasfi/siteinstitucional/auth/service/AdminUserService.java
package com.brasfi.siteinstitucional.auth.service;

import com.brasfi.siteinstitucional.auth.dto.AdminUserCreateRequest;
import com.brasfi.siteinstitucional.auth.entity.Perfil;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.PerfilRepository;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public void createAdminUser(AdminUserCreateRequest request) {
        Perfil adminRole = perfilRepository.findByNome("ADMIN")
                .orElseThrow(() -> new RuntimeException("Perfil ADMIN não encontrado"));

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .perfis(Collections.singleton(adminRole))
                .ativo(true)
                .build();

        usuarioRepository.save(usuario);
    }
}
