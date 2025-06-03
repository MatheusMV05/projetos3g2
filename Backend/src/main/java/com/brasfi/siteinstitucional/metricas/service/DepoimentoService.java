package com.brasfi.siteinstitucional.metricas.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.metricas.dto.DepoimentoDTO;
import com.brasfi.siteinstitucional.metricas.entity.Depoimento;
import com.brasfi.siteinstitucional.metricas.repository.DepoimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepoimentoService {

    private final DepoimentoRepository depoimentoRepository;

    public List<Depoimento> listarAtivos() {
        return depoimentoRepository.findByAtivoTrueOrderByOrdemAsc();
    }

    public List<Depoimento> listarDestaques() {
        return depoimentoRepository.findByDestaqueAndAtivoTrueOrderByOrdemAsc(true);
    }

    public List<Depoimento> listarPorAno(Integer ano) {
        return depoimentoRepository.findByAnoAndAtivoTrueOrderByOrdemAsc(ano);
    }

    public List<Depoimento> listarPorOrganizacao(String organizacao) {
        return depoimentoRepository.findByOrganizacaoAndAtivoTrueOrderByOrdemAsc(organizacao);
    }

    public List<Integer> listarAnosDisponiveis() {
        return depoimentoRepository.findDistinctAnosAtivos();
    }

    public Page<Depoimento> buscarComFiltros(
            Integer ano, String organizacao, Boolean destaque, Boolean ativo, Pageable pageable) {
        return depoimentoRepository.buscarComFiltros(ano, organizacao, destaque, ativo, pageable);
    }

    public Depoimento buscarPorId(Long id) {
        return depoimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Depoimento não encontrado"));
    }

    @Transactional
    @Auditavel(acao = "CRIAR", entidade = "DEPOIMENTO")
    public Depoimento criar(DepoimentoDTO dto) {
        Depoimento depoimento = Depoimento.builder()
                .nome(dto.getNome())
                .cargo(dto.getCargo())
                .organizacao(dto.getOrganizacao())
                .texto(dto.getTexto())
                .fotoUrl(dto.getFotoUrl())
                .iniciativaRelacionada(dto.getIniciativaRelacionada())
                .ano(dto.getAno())
                .ordem(dto.getOrdem())
                .destaque(dto.isDestaque())
                .ativo(dto.isAtivo())
                .build();

        return depoimentoRepository.save(depoimento);
    }

    @Transactional
    @Auditavel(acao = "ATUALIZAR", entidade = "DEPOIMENTO")
    public Depoimento atualizar(Long id, DepoimentoDTO dto) {
        Depoimento depoimento = buscarPorId(id);

        depoimento.setNome(dto.getNome());
        depoimento.setCargo(dto.getCargo());
        depoimento.setOrganizacao(dto.getOrganizacao());
        depoimento.setTexto(dto.getTexto());
        depoimento.setFotoUrl(dto.getFotoUrl());
        depoimento.setIniciativaRelacionada(dto.getIniciativaRelacionada());
        depoimento.setAno(dto.getAno());
        depoimento.setOrdem(dto.getOrdem());
        depoimento.setDestaque(dto.isDestaque());
        depoimento.setAtivo(dto.isAtivo());

        return depoimentoRepository.save(depoimento);
    }

    @Transactional
    @Auditavel(acao = "EXCLUIR", entidade = "DEPOIMENTO")
    public void excluir(Long id) {
        Depoimento depoimento = buscarPorId(id);

        // Soft delete - apenas desativa
        depoimento.setAtivo(false);
        depoimentoRepository.save(depoimento);
    }

    @Transactional
    @Auditavel(acao = "ATIVAR", entidade = "DEPOIMENTO")
    public Depoimento ativar(Long id) {
        Depoimento depoimento = buscarPorId(id);
        depoimento.setAtivo(true);
        return depoimentoRepository.save(depoimento);
    }

    @Transactional
    @Auditavel(acao = "DESATIVAR", entidade = "DEPOIMENTO")
    public Depoimento desativar(Long id) {
        Depoimento depoimento = buscarPorId(id);
        depoimento.setAtivo(false);
        return depoimentoRepository.save(depoimento);
    }

    @Transactional
    @Auditavel(acao = "DESTACAR", entidade = "DEPOIMENTO")
    public Depoimento alterarDestaque(Long id, boolean destaque) {
        Depoimento depoimento = buscarPorId(id);
        depoimento.setDestaque(destaque);
        return depoimentoRepository.save(depoimento);
    }
}