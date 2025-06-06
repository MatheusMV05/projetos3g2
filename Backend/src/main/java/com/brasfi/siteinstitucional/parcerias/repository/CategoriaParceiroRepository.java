package com.brasfi.siteinstitucional.parcerias.repository;

import com.brasfi.siteinstitucional.parcerias.entity.CategoriaParceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaParceiroRepository extends JpaRepository<CategoriaParceiro, Long> {
    Optional<CategoriaParceiro> findByNome(String nome);
    boolean existsByNome(String nome);
}