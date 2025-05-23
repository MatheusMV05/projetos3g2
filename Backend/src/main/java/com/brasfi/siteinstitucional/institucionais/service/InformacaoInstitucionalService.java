package com.brasfi.siteinstitucional.institucionais.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.institucionais.dto.InformacaoInstitucionalDTO;
import com.brasfi.siteinstitucional.institucionais.entity.InformacaoInstitucional;
import com.brasfi.siteinstitucional.institucionais.repository.InformacaoInstitucionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InformacaoInstitucionalService {

    private final InformacaoInstitucionalRepository infoRepository;

    @Cacheable("informacoes-institucionais")
    public InformacaoInstitucional buscarPorChave(String chave) {
        return infoRepository.findByChave(chave)
                .orElseThrow(() -> new ResourceNotFoundException("Informação institucional não encontrada com a chave: " + chave));
    }

    @Cacheable("informacoes-institucionais-all")
    public List<InformacaoInstitucional> listarTodas() {
        return infoRepository.findAll();
    }

    @Cacheable("informacoes-institucionais-ativas")
    public List<InformacaoInstitucional> listarAtivas() {
        return infoRepository.findByAtivo(true);
    }

    @Cacheable("informacoes-institucionais-tipo")
    public List<InformacaoInstitucional> listarPorTipo(String tipo) {
        return infoRepository.findByTipo(tipo);
    }

    @Cacheable("informacoes-institucionais-ativas-tipo")
    public List<InformacaoInstitucional> listarAtivasPorTipo(String tipo) {
        return infoRepository.findByAtivoAndTipo(true, tipo);
    }

    @Cacheable("informacoes-institucionais-map")
    public Map<String, String> listarAtivasComoMapa() {
        return infoRepository.findByAtivo(true).stream()
                .collect(Collectors.toMap(
                        InformacaoInstitucional::getChave,
                        InformacaoInstitucional::getValor
                ));
    }

    @Cacheable("informacoes-institucionais-map-tipo")
    public Map<String, String> listarAtivasComoMapaPorTipo(String tipo) {
        return infoRepository.findByAtivoAndTipo(true, tipo).stream()
                .collect(Collectors.toMap(
                        InformacaoInstitucional::getChave,
                        InformacaoInstitucional::getValor
                ));
    }

    @Auditavel(acao = "CRIAR", entidade = "INFORMACAO_INSTITUCIONAL")
    @CacheEvict(value = {"informacoes-institucionais", "informacoes-institucionais-all",
            "informacoes-institucionais-ativas", "informacoes-institucionais-tipo",
            "informacoes-institucionais-ativas-tipo", "informacoes-institucionais-map",
            "informacoes-institucionais-map-tipo"}, allEntries = true)
    public InformacaoInstitucional criar(InformacaoInstitucionalDTO dto) {
        // Verificar se já existe
        if (infoRepository.existsByChave(dto.getChave())) {
            throw new IllegalArgumentException("Já existe uma informação institucional com a chave: " + dto.getChave());
        }

        InformacaoInstitucional info = InformacaoInstitucional.builder()
                .chave(dto.getChave())
                .valor(dto.getValor())
                .tipo(dto.getTipo())
                .descricao(dto.getDescricao())
                .ativo(dto.isAtivo())
                .build();

        return infoRepository.save(info);
    }

    @Auditavel(acao = "ATUALIZAR", entidade = "INFORMACAO_INSTITUCIONAL")
    @CacheEvict(value = {"informacoes-institucionais", "informacoes-institucionais-all",
            "informacoes-institucionais-ativas", "informacoes-institucionais-tipo",
            "informacoes-institucionais-ativas-tipo", "informacoes-institucionais-map",
            "informacoes-institucionais-map-tipo"}, allEntries = true)
    public InformacaoInstitucional atualizar(Long id, InformacaoInstitucionalDTO dto) {
        InformacaoInstitucional infoExistente = infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Informação institucional não encontrada com o id: " + id));

        // Verificar se a chave mudou e se já existe
        if (!infoExistente.getChave().equals(dto.getChave()) && infoRepository.existsByChave(dto.getChave())) {
            throw new IllegalArgumentException("Já existe uma informação institucional com a chave: " + dto.getChave());
        }

        infoExistente.setChave(dto.getChave());
        infoExistente.setValor(dto.getValor());
        infoExistente.setTipo(dto.getTipo());
        infoExistente.setDescricao(dto.getDescricao());
        infoExistente.setAtivo(dto.isAtivo());

        return infoRepository.save(infoExistente);
    }

    @Auditavel(acao = "EXCLUIR", entidade = "INFORMACAO_INSTITUCIONAL")
    @CacheEvict(value = {"informacoes-institucionais", "informacoes-institucionais-all",
            "informacoes-institucionais-ativas", "informacoes-institucionais-tipo",
            "informacoes-institucionais-ativas-tipo", "informacoes-institucionais-map",
            "informacoes-institucionais-map-tipo"}, allEntries = true)
    public void excluir(Long id) {
        InformacaoInstitucional info = infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Informação institucional não encontrada com o id: " + id));

        infoRepository.delete(info);
    }

    @Auditavel(acao = "ALTERAR_ESTADO", entidade = "INFORMACAO_INSTITUCIONAL")
    @CacheEvict(value = {"informacoes-institucionais", "informacoes-institucionais-all",
            "informacoes-institucionais-ativas", "informacoes-institucionais-tipo",
            "informacoes-institucionais-ativas-tipo", "informacoes-institucionais-map",
            "informacoes-institucionais-map-tipo"}, allEntries = true)
    public InformacaoInstitucional alterarEstado(Long id, boolean ativo) {
        InformacaoInstitucional info = infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Informação institucional não encontrada com o id: " + id));

        info.setAtivo(ativo);
        return infoRepository.save(info);
    }
}