package com.brasfi.siteinstitucional.i18n.repository;

import com.brasfi.siteinstitucional.i18n.entity.Traducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TraducaoRepository extends JpaRepository<Traducao, Long> {
    Optional<Traducao> findByChaveAndIdioma(String chave, String idioma);
    List<Traducao> findByIdioma(String idioma);
    boolean existsByChaveAndIdioma(String chave, String idioma);

    @Query("SELECT t FROM Traducao t WHERE t.idioma = :idioma AND t.chave LIKE CONCAT(:prefixo, '%')")
    List<Traducao> findByIdiomaAndChaveStartingWith(@Param("idioma") String idioma, @Param("prefixo") String prefixo);
}