package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.dto.PaginaDTO;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.model.Pagina;
import com.brasfi.siteinstitucional.repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaginaService {

    @Autowired
    private PaginaRepository paginaRepository;

    public List<Pagina> listarTodas() {
        return paginaRepository.findAll();
    }

    public Pagina buscarPorId(Long id) {
        return paginaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Página não encontrada com o id: " + id));
    }

    public Pagina buscarPorSlug(String slug) {
        return paginaRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Página não encontrada com o slug: " + slug));
    }

    public Pagina salvar(PaginaDTO paginaDTO) {
        Pagina pagina = new Pagina();
        pagina.setTitulo(paginaDTO.getTitulo());
        pagina.setSlug(paginaDTO.getSlug());
        pagina.setConteudo(paginaDTO.getConteudo());

        return paginaRepository.save(pagina);
    }

    public Pagina atualizar(Long id, PaginaDTO paginaDTO) {
        Pagina paginaExistente = buscarPorId(id);

        paginaExistente.setTitulo(paginaDTO.getTitulo());
        paginaExistente.setSlug(paginaDTO.getSlug());
        paginaExistente.setConteudo(paginaDTO.getConteudo());

        return paginaRepository.save(paginaExistente);
    }

    public void excluir(Long id) {
        Pagina pagina = buscarPorId(id);
        paginaRepository.delete(pagina);
    }
}