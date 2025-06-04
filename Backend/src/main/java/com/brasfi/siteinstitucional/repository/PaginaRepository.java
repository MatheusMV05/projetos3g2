package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Pagina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaginaRepository extends JpaRepository<Pagina, Long> {
    Optional<Pagina> findBySlug(String slug);
    boolean existsBySlug(String slug);

    // Novo método para busca full-text em Pagina
    @Query(value = "SELECT p FROM Pagina p WHERE " +
            "to_tsvector('portuguese', p.titulo || ' ' || p.conteudo) @@ plainto_tsquery('portuguese', :searchTerm) " +
            "ORDER BY ts_rank(to_tsvector('portuguese', p.titulo || ' ' || p.conteudo), plainto_tsquery('portuguese', :searchTerm)) DESC",
            countQuery = "SELECT count(p) FROM Pagina p WHERE " +
                    "to_tsvector('portuguese', p.titulo || ' ' || p.conteudo) @@ plainto_tsquery('portuguese', :searchTerm)")
    Page<Pagina> searchByTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}