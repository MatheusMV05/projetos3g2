package com.brasfi.siteinstitucional.admin.backup.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackupService {

    private final DataSource dataSource;
    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;

    @Value("${application.backup.directory:./backups}")
    private String backupDirectory;

    @Value("${application.backup.tables:usuarios,perfis,paginas,informacoes_institucionais}")
    private String backupTables;

    @Auditavel(acao = "BACKUP", entidade = "SISTEMA", incluirParametros = false)
    @Scheduled(cron = "0 0 1 * * ?") // Executar todos os dias às 01:00
    public void realizarBackupDiario() {
        realizarBackup("diario");
    }

    @Auditavel(acao = "BACKUP", entidade = "SISTEMA", incluirParametros = false)
    @Scheduled(cron = "0 0 2 ? * MON") // Executar toda segunda-feira às 02:00
    public void realizarBackupSemanal() {
        realizarBackup("semanal");
    }

    @Auditavel(acao = "BACKUP", entidade = "SISTEMA", incluirParametros = false)
    public String realizarBackup(String tipo) {
        try {
            // Criar diretório de backup se não existir
            Path backupPath = Paths.get(backupDirectory);
            if (!Files.exists(backupPath)) {
                Files.createDirectories(backupPath);
            }

            // Gerar nome do arquivo de backup com timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nomeArquivo = tipo + "_backup_" + timestamp + ".json";
            Path arquivoBackup = backupPath.resolve(nomeArquivo);

            // Obter lista de tabelas para backup
            String[] tabelas = backupTables.split(",");

            // Criar objeto JSON para armazenar dados de backup
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("data_backup", LocalDateTime.now().toString());
            rootNode.put("tipo", tipo);

            // Para cada tabela, extrair dados
            for (String tabela : tabelas) {
                extrairDadosTabela(tabela.trim(), rootNode);
            }

            // Escrever arquivo JSON
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivoBackup.toFile(), rootNode);

            log.info("Backup {} realizado com sucesso: {}", tipo, arquivoBackup);
            return arquivoBackup.toString();

        } catch (Exception e) {
            log.error("Erro ao realizar backup {}: {}", tipo, e.getMessage(), e);
            throw new RuntimeException("Erro ao realizar backup: " + e.getMessage(), e);
        }
    }

    private void extrairDadosTabela(String nomeTabela, ObjectNode rootNode) {
        try {
            // Obter colunas da tabela
            List<String> colunas = obterColunasTabela(nomeTabela);

            // Consultar dados da tabela
            String sql = "SELECT * FROM " + nomeTabela;
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

            // Converter para formato JSON
            List<JsonNode> jsonRows = new ArrayList<>();
            for (Map<String, Object> row : rows) {
                ObjectNode jsonRow = objectMapper.createObjectNode();
                for (String coluna : colunas) {
                    Object valor = row.get(coluna);
                    if (valor == null) {
                        jsonRow.putNull(coluna);
                    } else if (valor instanceof Number) {
                        jsonRow.put(coluna, (Short) valor);
                    } else if (valor instanceof Boolean) {
                        jsonRow.put(coluna, (Boolean) valor);
                    } else {
                        jsonRow.put(coluna, valor.toString());
                    }
                }
                jsonRows.add(jsonRow);
            }

            // Adicionar ao nó principal
            rootNode.putArray(nomeTabela).addAll(jsonRows);

        } catch (Exception e) {
            log.error("Erro ao extrair dados da tabela {}: {}", nomeTabela, e.getMessage(), e);
        }
    }

    private List<String> obterColunasTabela(String nomeTabela) {
        List<String> colunas = new ArrayList<>();

        // Consultar metadados da tabela
        String sql = "SELECT column_name FROM information_schema.columns " +
                "WHERE table_name = ? ORDER BY ordinal_position";
        jdbcTemplate.query(sql, rs -> {
            colunas.add(rs.getString("column_name"));
        }, nomeTabela);

        return colunas;
    }

    public List<String> listarBackups() {
        try {
            List<String> arquivos = new ArrayList<>();
            File diretorio = new File(backupDirectory);

            if (diretorio.exists() && diretorio.isDirectory()) {
                File[] backups = diretorio.listFiles((dir, name) -> name.endsWith(".json"));
                if (backups != null) {
                    for (File backup : backups) {
                        arquivos.add(backup.getName());
                    }
                }
            }

            return arquivos;
        } catch (Exception e) {
            log.error("Erro ao listar backups: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao listar backups: " + e.getMessage(), e);
        }
    }

    public byte[] obterConteudoBackup(String nomeArquivo) {
        try {
            Path caminhoArquivo = Paths.get(backupDirectory, nomeArquivo);
            if (!Files.exists(caminhoArquivo)) {
                throw new IllegalArgumentException("Arquivo de backup não encontrado: " + nomeArquivo);
            }

            return Files.readAllBytes(caminhoArquivo);
        } catch (Exception e) {
            log.error("Erro ao obter conteúdo do backup {}: {}", nomeArquivo, e.getMessage(), e);
            throw new RuntimeException("Erro ao obter conteúdo do backup: " + e.getMessage(), e);
        }
    }
}