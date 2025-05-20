// Pertence à etapa: Desenvolver API CRUD para métricas
package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.dto.MetricaDTO;
import com.brasfi.siteinstitucional.entity.Metrica;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.repository.MetricaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricaService {


    @Autowired
    private MetricaRepository metricaRepository;

    @Cacheable("metricas")
    public List<Metrica> listarTodasSemFiltro() {
        return metricaRepository.findAll();
    }

    public Page<Metrica> listarTodas(String nome, Pageable pageable) {
        if (nome != null && !nome.isEmpty()) {
            return metricaRepository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            return metricaRepository.findAll(pageable);
        }
    }

    public Metrica buscarPorId(Long id) {
        return metricaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Métrica não encontrada com id: " + id));
    }

    public Metrica salvar(MetricaDTO dto) {
        Metrica metrica = new Metrica();
        metrica.setNome(dto.getNome());
        metrica.setDescricao(dto.getDescricao());
        metrica.setValor(dto.getValor());
        metrica.setUnidade(dto.getUnidade());
        metrica.setDataReferencia(dto.getDataReferencia());
        return metricaRepository.save(metrica);
    }

    public Metrica atualizar(Long id, MetricaDTO dto) {
        Metrica existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setValor(dto.getValor());
        existente.setUnidade(dto.getUnidade());
        existente.setDataReferencia(dto.getDataReferencia());
        return metricaRepository.save(existente);
    }

    public void excluir(Long id) {
        Metrica metrica = buscarPorId(id);
        metricaRepository.delete(metrica);
    }
}
