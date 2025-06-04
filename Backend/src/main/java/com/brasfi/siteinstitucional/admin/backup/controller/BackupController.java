package com.brasfi.siteinstitucional.admin.backup.controller;

import com.brasfi.siteinstitucional.admin.backup.service.BackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/backup")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class BackupController {

    private final BackupService backupService;

    @PostMapping
    public ResponseEntity<String> realizarBackup(@RequestParam(defaultValue = "manual") String tipo) {
        String caminhoBackup = backupService.realizarBackup(tipo);
        return ResponseEntity.ok("Backup realizado com sucesso: " + caminhoBackup);
    }

    @GetMapping
    public ResponseEntity<List<String>> listarBackups() {
        return ResponseEntity.ok(backupService.listarBackups());
    }

    @GetMapping("/{nomeArquivo}")
    public ResponseEntity<byte[]> baixarBackup(@PathVariable String nomeArquivo) {
        byte[] conteudo = backupService.obterConteudoBackup(nomeArquivo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", nomeArquivo);

        return ResponseEntity.ok()
                .headers(headers)
                .body(conteudo);
    }
}