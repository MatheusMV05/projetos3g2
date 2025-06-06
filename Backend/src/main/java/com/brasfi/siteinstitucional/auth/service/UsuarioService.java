package com.brasfi.siteinstitucional.auth.service;

import com.brasfi.siteinstitucional.auth.dto.AdminUserCreateRequest;
import com.brasfi.siteinstitucional.auth.entity.Perfil;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.PerfilRepository;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdminUser(AdminUserCreateRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setAtivo(true);

        Perfil perfilAdmin = perfilRepository.findByNome("ADMIN")
                .orElseThrow(() -> new RuntimeException("Perfil ADMIN não encontrado"));

        usuario.setPerfis(Collections.singleton(perfilAdmin));

        // TODO: salvar foto se necessário

        usuarioRepository.save(usuario);
    }
}
