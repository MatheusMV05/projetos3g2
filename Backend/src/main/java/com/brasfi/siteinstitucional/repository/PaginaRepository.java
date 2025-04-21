package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.model.Pagina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaginaRepository extends JpaRepository<Pagina, Long> {
    Optional<Pagina> findBySlug(String slug);
    boolean existsBySlug(String slug);
}