package com.brasfi.siteinstitucional.i18n.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.i18n.dto.TraducaoDTO;
import com.brasfi.siteinstitucional.i18n.entity.Traducao;
import com.brasfi.siteinstitucional.i18n.repository.TraducaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraducaoService {

    private final TraducaoRepository traducaoRepository;

    @Cacheable(value = "traducoes", key = "#idioma")
    public Map<String, String> getTraducoesPorIdioma(String idioma) {
        return traducaoRepository.findByIdioma(idioma).stream()
                .collect(Collectors.toMap(Traducao::getChave, Traducao::getValor));
    }

    @Cacheable(value = "traducoes", key = "#idioma + '-' + #prefixo")
    public Map<String, String> getTraducoesPorIdiomaEPrefixo(String idioma, String prefixo) {
        return traducaoRepository.findByIdiomaAndChaveStartingWith(idioma, prefixo).stream()
                .collect(Collectors.toMap(Traducao::getChave, Traducao::getValor));
    }

    @Cacheable(value = "traducao_especifica", key = "#idioma + '-' + #chave")
    public String getTraducao(String idioma, String chave) {
        return traducaoRepository.findByChaveAndIdioma(chave, idioma)
                .map(Traducao::getValor)
                .orElse(null); // Ou lançar exceção, ou retornar a chave como fallback
    }

    @Transactional
    @Auditavel(acao = "CRIAR_OU_ATUALIZAR", entidade = "TRADUCAO")
    @CacheEvict(value = {"traducoes", "traducao_especifica"}, allEntries = true)
    public TraducaoDTO salvarTraducao(TraducaoDTO dto) {
        Traducao traducao = traducaoRepository.findByChaveAndIdioma(dto.getChave(), dto.getIdioma())
                .orElseGet(() -> Traducao.builder()
                        .chave(dto.getChave())
                        .idioma(dto.getIdioma())
                        .build());
        traducao.setValor(dto.getValor());
        Traducao salva = traducaoRepository.save(traducao);
        return convertToDTO(salva);
    }

    @Transactional
    @Auditavel(acao = "CRIAR_EM_LOTE", entidade = "TRADUCAO")
    @CacheEvict(value = {"traducoes", "traducao_especifica"}, allEntries = true)
    public List<TraducaoDTO> salvarTraducoesEmLote(List<TraducaoDTO> dtos) {
        return dtos.stream().map(this::salvarTraducao).collect(Collectors.toList());
    }


    public Page<TraducaoDTO> listarTraducoesAdmin(Pageable pageable) {
        return traducaoRepository.findAll(pageable).map(this::convertToDTO);
    }

    public TraducaoDTO buscarTraducaoPorId(Long id) {
        return traducaoRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Tradução não encontrada com ID: " + id));
    }

    @Transactional
    @Auditavel(acao = "EXCLUIR", entidade = "TRADUCAO")
    @CacheEvict(value = {"traducoes", "traducao_especifica"}, allEntries = true)
    public void excluirTraducao(Long id) {
        if (!traducaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tradução não encontrada com ID: " + id);
        }
        traducaoRepository.deleteById(id);
    }

    private TraducaoDTO convertToDTO(Traducao t) {
        return TraducaoDTO.builder()
                .id(t.getId())
                .chave(t.getChave())
                .idioma(t.getIdioma())
                .valor(t.getValor())
                .build();
    }
}