package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Governanca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GovernancaRepository extends JpaRepository<Governanca, Long> {
    Optional<Governanca> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
