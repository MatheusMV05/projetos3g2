package com.brasfi.siteinstitucional.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev", matchIfMissing = true)
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class DevInfoController {

    @GetMapping("/login-info")
    public ResponseEntity<Map<String, Object>> getLoginInfo() {
        Map<String, Object> loginInfo = new HashMap<>();

        loginInfo.put("message", "Informações de login para desenvolvimento");
        loginInfo.put("adminUser", Map.of(
                "email", "admin@brasfi.com.br",
                "password", "123456",
                "description", "Usuário administrador padrão criado automaticamente"
        ));

        loginInfo.put("endpoints", Map.of(
                "login", "/api/auth/authenticate",
                "createUser", "/api/usuarios",
                "test", "/api/usuarios/test"
        ));

        loginInfo.put("note", "Este endpoint só está disponível em modo de desenvolvimento");

        return ResponseEntity.ok(loginInfo);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();

        status.put("environment", "development");
        status.put("timestamp", System.currentTimeMillis());
        status.put("server", "Spring Boot - BRASFI Backend");
        status.put("message", "Sistema funcionando corretamente");

        return ResponseEntity.ok(status);
    }
}