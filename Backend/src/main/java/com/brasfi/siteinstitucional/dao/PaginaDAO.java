package com.brasfi.siteinstitucional.dao;

import com.brasfi.siteinstitucional.entity.Pagina;
import java.util.List;
import java.util.Optional;

public interface PaginaDAO {
    List<Pagina> findAll();
    Optional<Pagina> findById(Long id);
    Optional<Pagina> findBySlug(String slug);
    Pagina save(Pagina pagina);
    void delete(Pagina pagina);
    boolean existsBySlug(String slug);
}