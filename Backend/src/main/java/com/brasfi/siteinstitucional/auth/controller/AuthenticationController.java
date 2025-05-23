package com.brasfi.siteinstitucional.auth.controller;

import com.brasfi.siteinstitucional.auth.dto.AuthenticationRequest;
import com.brasfi.siteinstitucional.auth.dto.AuthenticationResponse;
import com.brasfi.siteinstitucional.auth.dto.MfaVerificationRequest;
import com.brasfi.siteinstitucional.auth.dto.RegisterRequest;
import com.brasfi.siteinstitucional.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/mfa/verify")
    public ResponseEntity<AuthenticationResponse> verifyMfaCode(
            @Valid @RequestBody MfaVerificationRequest request
    ) {
        return ResponseEntity.ok(authService.verifyMfaCode(request));
    }
}