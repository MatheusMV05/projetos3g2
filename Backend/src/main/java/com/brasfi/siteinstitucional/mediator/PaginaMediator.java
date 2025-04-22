package com.brasfi.siteinstitucional.mediator;

import com.brasfi.siteinstitucional.dto.PaginaDTO;
import com.brasfi.siteinstitucional.entity.Pagina;

import java.util.List;

public interface PaginaMediator {
    List<Pagina> listarTodas();
    Pagina buscarPorId(Long id);
    Pagina buscarPorSlug(String slug);
    Pagina salvar(PaginaDTO paginaDTO);
    Pagina atualizar(Long id, PaginaDTO paginaDTO);
    void excluir(Long id);
}