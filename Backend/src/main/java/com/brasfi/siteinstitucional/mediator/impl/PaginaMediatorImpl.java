
package com.brasfi.siteinstitucional.mediator.impl;

import com.brasfi.siteinstitucional.dao.PaginaDAO;
import com.brasfi.siteinstitucional.dto.PaginaDTO;
import com.brasfi.siteinstitucional.entity.Pagina;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.mediator.PaginaMediator;
import com.brasfi.siteinstitucional.repository.PaginaRepository; // Import PaginaRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginaMediatorImpl implements PaginaMediator {

    @Autowired
    private PaginaDAO paginaDAO;

    @Autowired // Adicionar injeção para usar o novo método
    private PaginaRepository paginaRepository;


    @Override
    public List<Pagina> listarTodas() {
        return paginaDAO.findAll();
    }

    @Override
    public Pagina buscarPorId(Long id) {
        return paginaDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Página não encontrada com o id: " + id));
    }

    @Override
    public Pagina buscarPorSlug(String slug) {
        return paginaDAO.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Página não encontrada com o slug: " + slug));
    }

    @Override
    public Pagina salvar(PaginaDTO paginaDTO) {
        Pagina pagina = new Pagina();
        pagina.setTitulo(paginaDTO.getTitulo());
        pagina.setSlug(paginaDTO.getSlug());
        pagina.setConteudo(paginaDTO.getConteudo());

        return paginaDAO.save(pagina);
    }

    @Override
    public Pagina atualizar(Long id, PaginaDTO paginaDTO) {
        Pagina paginaExistente = buscarPorId(id);

        paginaExistente.setTitulo(paginaDTO.getTitulo());
        paginaExistente.setSlug(paginaDTO.getSlug());
        paginaExistente.setConteudo(paginaDTO.getConteudo());

        return paginaDAO.save(paginaExistente);
    }

    @Override
    public void excluir(Long id) {
        Pagina pagina = buscarPorId(id);
        paginaDAO.delete(pagina);
    }

    @Override // Implementação do novo método
    public Page<Pagina> searchPaginasByTerm(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return paginaRepository.findAll(pageable); // Ou retornar vazio, dependendo da lógica desejada
        }
        return paginaRepository.searchByTerm(searchTerm, pageable);
    }
}