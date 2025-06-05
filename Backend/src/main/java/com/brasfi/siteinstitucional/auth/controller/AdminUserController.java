package com.brasfi.siteinstitucional.auth.controller;

import com.brasfi.siteinstitucional.auth.dto.AdminUserCreateRequest;
import com.brasfi.siteinstitucional.auth.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAdminUser(
            @ModelAttribute @Valid AdminUserCreateRequest request
    ) {
        adminUserService.createAdminUser(request);
        return ResponseEntity.ok().build();
    }
}
