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

    // Query SQL nativa para PostgreSQL full-text search
    @Query(value = "SELECT * FROM pagina p WHERE " +
            "to_tsvector('portuguese', p.titulo || ' ' || p.conteudo) @@ plainto_tsquery('portuguese', :searchTerm) " +
            "ORDER BY ts_rank(to_tsvector('portuguese', p.titulo || ' ' || p.conteudo), plainto_tsquery('portuguese', :searchTerm)) DESC",
            countQuery = "SELECT count(*) FROM pagina p WHERE " +
                    "to_tsvector('portuguese', p.titulo || ' ' || p.conteudo) @@ plainto_tsquery('portuguese', :searchTerm)",
            nativeQuery = true)
    Page<Pagina> searchByTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}