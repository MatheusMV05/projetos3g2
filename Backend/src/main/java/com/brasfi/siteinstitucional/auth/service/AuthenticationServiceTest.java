package com.brasfi.siteinstitucional.auth.service;

import com.brasfi.siteinstitucional.auth.dto.AuthenticationRequest;
import com.brasfi.siteinstitucional.auth.dto.AuthenticationResponse;
import com.brasfi.siteinstitucional.auth.dto.RegisterRequest;
import com.brasfi.siteinstitucional.auth.entity.Perfil;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.auth.repository.PerfilRepository;
import com.brasfi.siteinstitucional.auth.repository.UsuarioRepository;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private GoogleAuthenticator googleAuthenticator;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authRequest;
    private Usuario usuario;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .nome("Test User")
                .email("test@example.com")
                .senha("password")
                .mfaAtivado(false)
                .build();

        authRequest = AuthenticationRequest.builder()
                .email("test@example.com")
                .senha("password")
                .build();

        perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNome("ADMIN");

        usuario = Usuario.builder()
                .id(1L)
                .nome("Test User")
                .email("test@example.com")
                .senha("encodedPassword")
                .perfis(Set.of(perfil))
                .ativo(true)
                .mfaAtivado(false)
                .build();
    }

    @Test
    void register_ShouldCreateUserAndReturnToken() {
        // Arrange
        when(perfilRepository.findByNome("ADMIN")).thenReturn(Optional.of(perfil));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.generateToken(any(Usuario.class))).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwtToken");
        assertThat(response.isRequiresMfa()).isFalse();
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void register_WithMfa_ShouldCreateUserWithMfaEnabled() {
        // Arrange
        registerRequest.setMfaAtivado(true);
        GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder("secretKey").build();

        when(perfilRepository.findByNome("ADMIN")).thenReturn(Optional.of(perfil));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(googleAuthenticator.createCredentials()).thenReturn(key);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.generateToken(any(Usuario.class))).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwtToken");
        assertThat(response.isRequiresMfa()).isTrue();
        assertThat(response.getMfaSecret()).isEqualTo("secretKey");
        verify(googleAuthenticator, times(1)).createCredentials();
    }

    @Test
    void register_ShouldThrowExceptionWhenPerfilNotFound() {
        // Arrange
        when(perfilRepository.findByNome("ADMIN")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            authenticationService.register(registerRequest);
        });

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void authenticate_WithoutMfa_ShouldReturnToken() {
        // Arrange
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(usuario)).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwtToken");
        assertThat(response.isRequiresMfa()).isFalse();
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void authenticate_WithMfa_ShouldRequireMfaVerification() {
        // Arrange
        usuario.setMfaAtivado(true);
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));

        // Act
        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNull();
        assertThat(response.isRequiresMfa()).isTrue();
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        verify(jwtService, never()).generateToken(any(Usuario.class));
    }

    @Test
    void authenticate_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            authenticationService.authenticate(authRequest);
        });
    }
}