package com.brasfi.siteinstitucional.metricas.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.auth.entity.Usuario;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.metricas.dto.MetricaImpactoDTO;
import com.brasfi.siteinstitucional.metricas.entity.HistoricoMetrica;
import com.brasfi.siteinstitucional.metricas.entity.MetricaImpacto;
import com.brasfi.siteinstitucional.metricas.repository.HistoricoMetricaRepository;
import com.brasfi.siteinstitucional.metricas.repository.MetricaImpactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetricaImpactoService {

    private final MetricaImpactoRepository metricaRepository;
    private final HistoricoMetricaRepository historicoRepository;

    public List<MetricaImpacto> listarAtivas() {
        return metricaRepository.findByAtivoTrueOrderByOrdemAsc();
    }

    public List<MetricaImpacto> listarPorAno(Integer ano) {
        return metricaRepository.findByAnoAndAtivoTrueOrderByOrdemAsc(ano);
    }

    public List<MetricaImpacto> listarPorCategoria(String categoria) {
        return metricaRepository.findByCategoriaAndAtivoTrueOrderByOrdemAsc(categoria);
    }

    public Map<String, List<MetricaImpacto>> listarAgrupadasPorCategoria(Integer ano) {
        List<MetricaImpacto> metricas = ano != null ?
                metricaRepository.findByAnoAndAtivoTrueOrderByOrdemAsc(ano) :
                metricaRepository.findByAtivoTrueOrderByOrdemAsc();

        return metricas.stream()
                .collect(Collectors.groupingBy(MetricaImpacto::getCategoria));
    }

    public Map<Integer, List<MetricaImpacto>> listarAgrupadasPorAno(String categoria) {
        List<MetricaImpacto> metricas = categoria != null ?
                metricaRepository.findByCategoriaAndAtivoTrueOrderByOrdemAsc(categoria) :
                metricaRepository.findByAtivoTrueOrderByOrdemAsc();

        return metricas.stream()
                .collect(Collectors.groupingBy(MetricaImpacto::getAno));
    }

    public List<Integer> listarAnosDisponiveis() {
        return metricaRepository.findDistinctAnosAtivos();
    }

    public List<String> listarCategoriasDisponiveis() {
        return metricaRepository.findDistinctCategoriasAtivas();
    }

    public Page<MetricaImpacto> buscarComFiltros(
            Integer ano, String categoria, String tipoIniciativa, Boolean ativo, Pageable pageable) {
        return metricaRepository.buscarComFiltros(ano, categoria, tipoIniciativa, ativo, pageable);
    }

    public MetricaImpacto buscarPorId(Long id) {
        return metricaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Métrica não encontrada"));
    }

    @Transactional
    @Auditavel(acao = "CRIAR", entidade = "METRICA_IMPACTO")
    public MetricaImpacto criar(MetricaImpactoDTO dto) {
        MetricaImpacto metrica = MetricaImpacto.builder()
                .titulo(dto.getTitulo())
                .valor(dto.getValor())
                .unidade(dto.getUnidade())
                .descricao(dto.getDescricao())
                .categoria(dto.getCategoria())
                .ano(dto.getAno())
                .tipoIniciativa(dto.getTipoIniciativa())
                .icone(dto.getIcone())
                .ordem(dto.getOrdem())
                .ativo(dto.isAtivo())
                .build();

        return metricaRepository.save(metrica);
    }

    @Transactional
    @Auditavel(acao = "ATUALIZAR", entidade = "METRICA_IMPACTO")
    public MetricaImpacto atualizar(Long id, MetricaImpactoDTO dto) {
        MetricaImpacto metrica = buscarPorId(id);

        // Registrar histórico se o valor mudou
        if (!metrica.getValor().equals(dto.getValor())) {
            registrarHistorico(metrica, dto.getValor(), "Atualização de valor");
        }

        metrica.setTitulo(dto.getTitulo());
        metrica.setValor(dto.getValor());
        metrica.setUnidade(dto.getUnidade());
        metrica.setDescricao(dto.getDescricao());
        metrica.setCategoria(dto.getCategoria());
        metrica.setAno(dto.getAno());
        metrica.setTipoIniciativa(dto.getTipoIniciativa());
        metrica.setIcone(dto.getIcone());
        metrica.setOrdem(dto.getOrdem());
        metrica.setAtivo(dto.isAtivo());

        return metricaRepository.save(metrica);
    }

    @Transactional
    @Auditavel(acao = "EXCLUIR", entidade = "METRICA_IMPACTO")
    public void excluir(Long id) {
        MetricaImpacto metrica = buscarPorId(id);

        // Soft delete - apenas desativa
        metrica.setAtivo(false);
        metricaRepository.save(metrica);
    }

    @Transactional
    @Auditavel(acao = "ATIVAR", entidade = "METRICA_IMPACTO")
    public MetricaImpacto ativar(Long id) {
        MetricaImpacto metrica = buscarPorId(id);
        metrica.setAtivo(true);
        return metricaRepository.save(metrica);
    }

    @Transactional
    @Auditavel(acao = "DESATIVAR", entidade = "METRICA_IMPACTO")
    public MetricaImpacto desativar(Long id) {
        MetricaImpacto metrica = buscarPorId(id);
        metrica.setAtivo(false);
        return metricaRepository.save(metrica);
    }

    private void registrarHistorico(MetricaImpacto metrica, BigDecimal novoValor, String descricao) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HistoricoMetrica historico = HistoricoMetrica.builder()
                .metrica(metrica)
                .valorAnterior(metrica.getValor())
                .valorNovo(novoValor)
                .descricaoAlteracao(descricao)
                .usuario(usuario)
                .build();

        historicoRepository.save(historico);
    }

    public Page<HistoricoMetrica> buscarHistorico(Long metricaId, Pageable pageable) {
        return historicoRepository.findByMetricaIdOrderByDataAlteracaoDesc(metricaId, pageable);
    }
}