package com.brasfi.siteinstitucional.parcerias.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.parcerias.dto.CategoriaParceiroDTO;
import com.brasfi.siteinstitucional.parcerias.entity.CategoriaParceiro;
import com.brasfi.siteinstitucional.parcerias.repository.CategoriaParceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaParceiroService {

    private final CategoriaParceiroRepository categoriaRepository;

    public List<CategoriaParceiro> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Page<CategoriaParceiro> listarPaginado(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    public CategoriaParceiro buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de parceiro não encontrada com id: " + id));
    }

    @Transactional
    @Auditavel(acao = "CRIAR", entidade = "CATEGORIA_PARCEIRO")
    public CategoriaParceiro criar(CategoriaParceiroDTO dto) {
        if (categoriaRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Categoria com nome '" + dto.getNome() + "' já existe.");
        }
        CategoriaParceiro categoria = CategoriaParceiro.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .build();
        return categoriaRepository.save(categoria);
    }

    @Transactional
    @Auditavel(acao = "ATUALIZAR", entidade = "CATEGORIA_PARCEIRO")
    public CategoriaParceiro atualizar(Long id, CategoriaParceiroDTO dto) {
        CategoriaParceiro categoria = buscarPorId(id);
        categoriaRepository.findByNome(dto.getNome()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new IllegalArgumentException("Categoria com nome '" + dto.getNome() + "' já existe.");
            }
        });

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    @Auditavel(acao = "EXCLUIR", entidade = "CATEGORIA_PARCEIRO")
    public void excluir(Long id) {
        // Adicionar verificação se a categoria está em uso antes de excluir, se necessário
        CategoriaParceiro categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }
}