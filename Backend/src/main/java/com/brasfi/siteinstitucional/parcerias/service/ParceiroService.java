package com.brasfi.siteinstitucional.parcerias.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.parcerias.dto.ParceiroDTO;
import com.brasfi.siteinstitucional.parcerias.dto.ParceiroSummaryDTO;
import com.brasfi.siteinstitucional.parcerias.entity.CategoriaParceiro;
import com.brasfi.siteinstitucional.parcerias.entity.Parceiro;
import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import com.brasfi.siteinstitucional.parcerias.repository.CategoriaParceiroRepository;
import com.brasfi.siteinstitucional.parcerias.repository.ParceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParceiroService {

    private final ParceiroRepository parceiroRepository;
    private final CategoriaParceiroRepository categoriaRepository;

    // Métodos Públicos (para o site)
    public List<ParceiroSummaryDTO> listarParceirosVisiveis() {
        return parceiroRepository.findByVisivelTrueAndAtivoTrueOrderByNomeOrganizacaoAsc().stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }

    public Page<ParceiroSummaryDTO> listarParceirosVisiveisPaginadoComFiltros(
            String nome, TipoParceria tipo, String setor, Long categoriaId, Pageable pageable) {
        return parceiroRepository.buscarComFiltros(nome, tipo, setor, categoriaId, true, true, pageable)
                .map(this::convertToSummaryDTO);
    }

    // Métodos Administrativos
    public Page<ParceiroDTO> buscarParceirosAdmin(
            String nome, TipoParceria tipo, String setor, Long categoriaId, Boolean visivel, Boolean ativo, Pageable pageable) {
        return parceiroRepository.buscarComFiltros(nome, tipo, setor, categoriaId, visivel, ativo, pageable)
                .map(this::convertToDTO);
    }

    public ParceiroDTO buscarParceiroPorIdAdmin(Long id) {
        Parceiro parceiro = parceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado com id: " + id));
        return convertToDTO(parceiro);
    }

    @Transactional
    @Auditavel(acao = "CRIAR", entidade = "PARCEIRO")
    public ParceiroDTO criarParceiro(ParceiroDTO dto) {
        Parceiro parceiro = convertToEntity(dto);
        parceiro.setAtivo(true); // Por padrão, novo parceiro é ativo
        Parceiro parceiroSalvo = parceiroRepository.save(parceiro);
        return convertToDTO(parceiroSalvo);
    }

    @Transactional
    @Auditavel(acao = "ATUALIZAR", entidade = "PARCEIRO")
    public ParceiroDTO atualizarParceiro(Long id, ParceiroDTO dto) {
        Parceiro parceiroExistente = parceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado com id: " + id));

        parceiroExistente.setNomeOrganizacao(dto.getNomeOrganizacao());
        parceiroExistente.setDescricao(dto.getDescricao());
        parceiroExistente.setLogoUrl(dto.getLogoUrl());
        parceiroExistente.setSiteUrl(dto.getSiteUrl());
        parceiroExistente.setTipoParceria(dto.getTipoParceria());
        parceiroExistente.setSetorAtuacao(dto.getSetorAtuacao());
        parceiroExistente.setDataInicioParceria(dto.getDataInicioParceria());
        parceiroExistente.setVisivel(dto.isVisivel());
        parceiroExistente.setAtivo(dto.isAtivo());

        if (dto.getCategoriaIds() != null) {
            Set<CategoriaParceiro> categorias = new HashSet<>(categoriaRepository.findAllById(dto.getCategoriaIds()));
            parceiroExistente.setCategorias(categorias);
        } else {
            parceiroExistente.getCategorias().clear();
        }

        Parceiro parceiroAtualizado = parceiroRepository.save(parceiroExistente);
        return convertToDTO(parceiroAtualizado);
    }

    @Transactional
    @Auditavel(acao = "ALTERAR_VISIBILIDADE", entidade = "PARCEIRO")
    public ParceiroDTO alterarVisibilidade(Long id, boolean visivel) {
        Parceiro parceiro = parceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado com id: " + id));
        parceiro.setVisivel(visivel);
        return convertToDTO(parceiroRepository.save(parceiro));
    }

    @Transactional
    @Auditavel(acao = "ALTERAR_ESTADO_ATIVO", entidade = "PARCEIRO")
    public ParceiroDTO alterarEstadoAtivo(Long id, boolean ativo) {
        Parceiro parceiro = parceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado"));
        parceiro.setAtivo(ativo);
        return convertToDTO(parceiroRepository.save(parceiro));
    }

    @Transactional
    @Auditavel(acao = "EXCLUIR_LOGICO", entidade = "PARCEIRO")
    public void excluirLogicoParceiro(Long id) {
        Parceiro parceiro = parceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado com id: " + id));
        parceiro.setAtivo(false); // Soft delete
        parceiroRepository.save(parceiro);
    }

    // --- Mappers ---
    private ParceiroDTO convertToDTO(Parceiro parceiro) {
        return ParceiroDTO.builder()
                .id(parceiro.getId())
                .nomeOrganizacao(parceiro.getNomeOrganizacao())
                .descricao(parceiro.getDescricao())
                .logoUrl(parceiro.getLogoUrl())
                .siteUrl(parceiro.getSiteUrl())
                .tipoParceria(parceiro.getTipoParceria())
                .setorAtuacao(parceiro.getSetorAtuacao())
                .categoriaIds(parceiro.getCategorias().stream().map(CategoriaParceiro::getId).collect(Collectors.toSet()))
                .dataInicioParceria(parceiro.getDataInicioParceria())
                .visivel(parceiro.isVisivel())
                .ativo(parceiro.isAtivo())
                .build();
    }

    private ParceiroSummaryDTO convertToSummaryDTO(Parceiro parceiro) {
        String descricaoCurta = parceiro.getDescricao();
        if (descricaoCurta != null && descricaoCurta.length() > 150) {
            descricaoCurta = descricaoCurta.substring(0, 147) + "...";
        }

        return ParceiroSummaryDTO.builder()
                .id(parceiro.getId())
                .nomeOrganizacao(parceiro.getNomeOrganizacao())
                .descricaoCurta(descricaoCurta)
                .logoUrl(parceiro.getLogoUrl())
                .siteUrl(parceiro.getSiteUrl())
                .tipoParceria(parceiro.getTipoParceria())
                .setorAtuacao(parceiro.getSetorAtuacao())
                .nomesCategorias(parceiro.getCategorias().stream().map(CategoriaParceiro::getNome).collect(Collectors.toList()))
                .build();
    }

    private Parceiro convertToEntity(ParceiroDTO dto) {
        Set<CategoriaParceiro> categorias = new HashSet<>();
        if (dto.getCategoriaIds() != null) {
            categorias.addAll(categoriaRepository.findAllById(dto.getCategoriaIds()));
        }
        return Parceiro.builder()
                .id(dto.getId())
                .nomeOrganizacao(dto.getNomeOrganizacao())
                .descricao(dto.getDescricao())
                .logoUrl(dto.getLogoUrl())
                .siteUrl(dto.getSiteUrl())
                .tipoParceria(dto.getTipoParceria())
                .setorAtuacao(dto.getSetorAtuacao())
                .categorias(categorias)
                .dataInicioParceria(dto.getDataInicioParceria())
                .visivel(dto.isVisivel())
                .ativo(dto.isAtivo())
                .build();
    }
}