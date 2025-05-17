package com.brasfi.siteinstitucional.auth.service;

import com.brasfi.siteinstitucional.auth.dto.AuthenticationRequest;
import com.brasfi.siteinstitucional.auth.dto.AuthenticationResponse;
import com.brasfi.siteinstitucional.auth.dto.MfaVerificationRequest;
import com.brasfi.siteinstitucional.auth.dto.RegisterRequest;
import com.brasfi.siteinstitucional.auth.entity.Perfil;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.PerfilRepository;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GoogleAuthenticator googleAuthenticator;

    public AuthenticationResponse register(RegisterRequest request) {
        Perfil perfilAdmin = perfilRepository.findByNome("ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));

        Set<Perfil> perfis = new HashSet<>();
        perfis.add(perfilAdmin);

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .perfis(perfis)
                .build();

        // Se MFA estiver habilitado, gerar secret
        if (request.isMfaAtivado()) {
            GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
            usuario.setMfaAtivado(true);
            usuario.setMfaSecret(key.getKey());
        }

        usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(usuario);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .requiresMfa(usuario.isMfaAtivado())
                .mfaSecret(usuario.getMfaSecret())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Se não tiver MFA, gera token e retorna
        if (!usuario.isMfaAtivado()) {
            String jwtToken = jwtService.generateToken(usuario);

            // Atualiza último login
            usuario.setUltimoLogin(LocalDateTime.now());
            usuarioRepository.save(usuario);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .requiresMfa(false)
                    .build();
        }

        // Se tiver MFA, retorna status que requer verificação
        return AuthenticationResponse.builder()
                .token(null)
                .requiresMfa(true)
                .email(usuario.getEmail())
                .build();
    }

    public AuthenticationResponse verifyMfaCode(MfaVerificationRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        boolean isCodeValid = googleAuthenticator.authorize(
                usuario.getMfaSecret(), request.getCode());

        if (!isCodeValid) {
            throw new IllegalArgumentException("Código MFA inválido");
        }

        String jwtToken = jwtService.generateToken(usuario);

        // Atualiza último login
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .requiresMfa(false)
                .build();
    }
}