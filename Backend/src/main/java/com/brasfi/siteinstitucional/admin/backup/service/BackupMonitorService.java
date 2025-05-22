package com.brasfi.siteinstitucional.admin.backup.service;

import com.brasfi.siteinstitucional.admin.notification.publisher.NotificacaoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackupMonitorService {

    private final NotificacaoPublisher notificacaoPublisher;

    @Value("${application.backup.directory:./backups}")
    private String backupDirectory;

    @Value("${application.backup.monitor.enabled:true}")
    private boolean monitorEnabled;

    @Value("${application.backup.monitor.tolerance-hours:25}")
    private int toleranceHours;

    @Scheduled(cron = "0 0 3 * * ?") // Executar todos os dias às 03:00
    public void verificarBackupsDiarios() {
        if (!monitorEnabled) {
            log.info("Monitoramento de backups desativado");
            return;
        }

        try {
            File diretorioBackup = new File(backupDirectory);
            if (!diretorioBackup.exists() || !diretorioBackup.isDirectory()) {
                notificarErro("Diretório de backup não encontrado",
                        "O diretório de backup '" + backupDirectory + "' não existe ou não é um diretório válido.");
                return;
            }

            // Verificar se há backup diário recente
            verificarBackupRecente("diario", toleranceHours);

            // Verificar backup semanal às segundas-feiras
            if (LocalDateTime.now().getDayOfWeek().getValue() == 1) { // Segunda-feira
                verificarBackupRecente("semanal", toleranceHours);
            }

        } catch (Exception e) {
            log.error("Erro ao verificar backups: {}", e.getMessage(), e);
            notificarErro("Erro ao verificar backups",
                    "Ocorreu um erro ao verificar os backups: " + e.getMessage());
        }
    }

    private void verificarBackupRecente(String tipo, int toleranciaHoras) {
        File diretorioBackup = new File(backupDirectory);
        File[] arquivos = diretorioBackup.listFiles((dir, nome) ->
                nome.startsWith(tipo + "_backup_") && nome.endsWith(".json"));

        if (arquivos == null || arquivos.length == 0) {
            notificarErro("Backup " + tipo + " não encontrado",
                    "Não foram encontrados arquivos de backup do tipo '" + tipo + "'.");
            return;
        }

        // Encontrar o backup mais recente
        File backupMaisRecente = null;
        LocalDateTime dataMaisRecente = LocalDateTime.MIN;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        for (File arquivo : arquivos) {
            try {
                String nomeArquivo = arquivo.getName();
                // Extrair a data do nome do arquivo (tipo_backup_yyyyMMdd_HHmmss.json)
                String dataStr = nomeArquivo.substring(
                        tipo.length() + "_backup_".length(),
                        nomeArquivo.length() - ".json".length());

                LocalDateTime dataBackup = LocalDateTime.parse(dataStr, formatter);

                if (dataBackup.isAfter(dataMaisRecente)) {
                    dataMaisRecente = dataBackup;
                    backupMaisRecente = arquivo;
                }
            } catch (Exception e) {
                log.warn("Erro ao processar arquivo de backup {}: {}", arquivo.getName(), e.getMessage());
            }
        }

        // Verificar se o backup mais recente está dentro da tolerância
        if (backupMaisRecente != null) {
            LocalDateTime agora = LocalDateTime.now();
            long horasDesdeUltimoBackup = ChronoUnit.HOURS.between(dataMaisRecente, agora);

            if (horasDesdeUltimoBackup > toleranciaHoras) {
                notificarErro("Backup " + tipo + " desatualizado",
                        "O último backup " + tipo + " foi realizado há " + horasDesdeUltimoBackup +
                                " horas (em " + dataMaisRecente.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                                "). A tolerância configurada é de " + toleranciaHoras + " horas.");
            } else {
                log.info("Backup {} em dia. Último backup: {}", tipo, dataMaisRecente);
            }
        }
    }

    private void notificarErro(String titulo, String mensagem) {
        log.error("{}: {}", titulo, mensagem);
        notificacaoPublisher.publicarParaAdmins(
                titulo,
                mensagem,
                "ERROR",
                "/admin/backup"
        );
    }
}