package com.brasfi.siteinstitucional.auth.controller;

import com.brasfi.siteinstitucional.auth.dto.AdminUserCreateRequest;
import com.brasfi.siteinstitucional.auth.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class UsuarioController {

    private final AdminUserService adminUserService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> createUser(
            @ModelAttribute @Valid AdminUserCreateRequest request) {
        try {
            log.info("Recebendo requisição para criar usuário");
            log.info("Nome: {}", request.getNome());
            log.info("Email: {}", request.getEmail());
            log.info("Foto enviada: {}", request.getFoto() != null ? "Sim" : "Não");

            // Validações básicas
            if (request.getNome() == null || request.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório");
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email é obrigatório");
            }

            if (request.getSenha() == null || request.getSenha().trim().isEmpty()) {
                throw new IllegalArgumentException("Senha é obrigatória");
            }

            adminUserService.createAdminUser(request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Usuário cadastrado com sucesso!");
            response.put("status", "success");
            response.put("email", request.getEmail());

            log.info("Usuário criado com sucesso: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação: {}", e.getMessage());

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("status", "validation_error");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            log.error("Erro inesperado ao criar usuário: {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro interno do servidor: " + e.getMessage());
            errorResponse.put("status", "error");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> testEndpoint(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Endpoint funcionando!");
        response.put("status", "success");
        response.put("user", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("timestamp", System.currentTimeMillis());

        log.info("Endpoint de teste acessado por: {}", authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/debug-auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> debugAuth(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", authentication.isAuthenticated());
        response.put("principal", authentication.getPrincipal().toString());
        response.put("authorities", authentication.getAuthorities().toString());
        response.put("name", authentication.getName());

        log.info("Debug auth - User: {}, Authorities: {}",
                authentication.getName(), authentication.getAuthorities());

        return ResponseEntity.ok(response);
    }
}