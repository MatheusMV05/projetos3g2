package com.brasfi.siteinstitucional.metricas.repository;

import com.brasfi.siteinstitucional.metricas.entity.MetricaImpacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetricaImpactoRepository extends JpaRepository<MetricaImpacto, Long> {

    List<MetricaImpacto> findByAtivoTrueOrderByOrdemAsc();

    List<MetricaImpacto> findByAnoAndAtivoTrueOrderByOrdemAsc(Integer ano);

    List<MetricaImpacto> findByCategoriaAndAtivoTrueOrderByOrdemAsc(String categoria);

    List<MetricaImpacto> findByAnoAndCategoriaAndAtivoTrueOrderByOrdemAsc(Integer ano, String categoria);

    @Query("SELECT DISTINCT m.ano FROM MetricaImpacto m WHERE m.ativo = true ORDER BY m.ano DESC")
    List<Integer> findDistinctAnosAtivos();

    @Query("SELECT DISTINCT m.categoria FROM MetricaImpacto m WHERE m.ativo = true ORDER BY m.categoria")
    List<String> findDistinctCategoriasAtivas();

    @Query("SELECT m FROM MetricaImpacto m WHERE " +
            "(:ano IS NULL OR m.ano = :ano) AND " +
            "(:categoria IS NULL OR m.categoria = :categoria) AND " +
            "(:tipoIniciativa IS NULL OR m.tipoIniciativa = :tipoIniciativa) AND " +
            "(:ativo IS NULL OR m.ativo = :ativo) " +
            "ORDER BY m.ordem ASC")
    Page<MetricaImpacto> buscarComFiltros(
            @Param("ano") Integer ano,
            @Param("categoria") String categoria,
            @Param("tipoIniciativa") String tipoIniciativa,
            @Param("ativo") Boolean ativo,
            Pageable pageable);
}