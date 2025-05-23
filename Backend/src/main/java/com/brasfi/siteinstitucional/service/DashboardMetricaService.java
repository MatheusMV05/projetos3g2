package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.dto.DashboardMetricaDTO;
import com.brasfi.siteinstitucional.dto.MetricaFiltroDTO;
import com.brasfi.siteinstitucional.entity.Metrica;
import com.brasfi.siteinstitucional.repository.MetricaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardMetricaService {

    private final MetricaRepository metricaRepository;

    /**
     * Gera dados agregados para o dashboard de métricas
     * @return DTO com os dados do dashboard
     */
    @Transactional(readOnly = true)
    public DashboardMetricaDTO gerarDadosDashboard() {
        List<Metrica> todasMetricas = metricaRepository.findAll();

        // Estatísticas gerais
        Map<String, Object> estatisticasGerais = metricaRepository.buscarEstatisticasGerais();

        // Métricas por unidade
        Map<String, Long> metricasPorUnidade = todasMetricas.stream()
                .collect(Collectors.groupingBy(Metrica::getUnidade, Collectors.counting()));

        // Evolução mensal
        List<Map<String, Object>> evolucaoMensalData = metricaRepository.buscarEvolucaoMensal();
        Map<Integer, Double> evolucaoMensal = new HashMap<>();

        for (Map<String, Object> entry : evolucaoMensalData) {
            Integer ano = ((Number) entry.get("ano")).intValue();
            Integer mes = ((Number) entry.get("mes")).intValue();
            Double media = ((Number) entry.get("media")).doubleValue();

            // Formato: YYYYMM (ex: 202305 para maio de 2023)
            Integer chave = ano * 100 + mes;
            evolucaoMensal.put(chave, media);
        }

        // Últimas métricas adicionadas
        List<Map<String, Object>> ultimasMetricas = todasMetricas.stream()
                .sorted(Comparator.comparing(Metrica::getDataReferencia).reversed())
                .limit(5)
                .map(m -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("id", m.getId());
                    item.put("nome", m.getNome());
                    item.put("valor", m.getValor());
                    item.put("unidade", m.getUnidade());
                    item.put("dataReferencia", m.getDataReferencia());
                    return item;
                })
                .collect(Collectors.toList());

        return new DashboardMetricaDTO(
                ((Number) estatisticasGerais.get("total")).intValue(),
                new BigDecimal(estatisticasGerais.get("media").toString()),
                new BigDecimal(estatisticasGerais.get("maximo").toString()),
                new BigDecimal(estatisticasGerais.get("minimo").toString()),
                metricasPorUnidade,
                evolucaoMensal,
                ultimasMetricas
        );
    }

    /**
     * Retorna estatísticas específicas por unidade de medida
     * @return Lista de objetos Map com dados agregados
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obterEstatisticasPorUnidade() {
        return metricaRepository.buscarEstatisticasPorUnidade();
    }
}