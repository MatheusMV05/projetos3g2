package com.brasfi.siteinstitucional.admin.analytics.controller;

import com.brasfi.siteinstitucional.admin.analytics.dto.EstatisticasDTO;
import com.brasfi.siteinstitucional.admin.analytics.dto.EventoAnalyticsDTO;
import com.brasfi.siteinstitucional.admin.analytics.service.AnalyticsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PostMapping("/api/public/analytics/evento")
    public ResponseEntity<Void> registrarEvento(
            @Valid @RequestBody EventoAnalyticsDTO dto,
            HttpServletRequest request) {
        analyticsService.registrarEvento(dto, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/admin/analytics/estatisticas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstatisticasDTO> obterEstatisticas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(analyticsService.obterEstatisticas(inicio, fim));
    }

    @GetMapping("/api/admin/analytics/estatisticas/diarias")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstatisticasDTO> obterEstatisticasDiarias() {
        return ResponseEntity.ok(analyticsService.obterEstatisticasDiarias());
    }

    @GetMapping("/api/admin/analytics/estatisticas/semanais")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstatisticasDTO> obterEstatisticasSemanais() {
        return ResponseEntity.ok(analyticsService.obterEstatisticasSemanais());
    }

    @GetMapping("/api/admin/analytics/estatisticas/mensais")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstatisticasDTO> obterEstatisticasMensais() {
        return ResponseEntity.ok(analyticsService.obterEstatisticasMensais());
    }
}