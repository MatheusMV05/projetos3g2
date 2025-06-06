// Path: matheusmv05/projetos3g2/projetos3g2-backend/Backend/src/main/java/com/brasfi/siteinstitucional/mediator/PaginaMediator.java
package com.brasfi.siteinstitucional.mediator;

import com.brasfi.siteinstitucional.dto.PaginaDTO;
import com.brasfi.siteinstitucional.entity.Pagina;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable

import java.util.List;

public interface PaginaMediator {
    List<Pagina> listarTodas();
    Pagina buscarPorId(Long id);
    Pagina buscarPorSlug(String slug);
    Pagina salvar(PaginaDTO paginaDTO);
    Pagina atualizar(Long id, PaginaDTO paginaDTO);
    void excluir(Long id);
    Page<Pagina> searchPaginasByTerm(String searchTerm, Pageable pageable); // Novo método
}