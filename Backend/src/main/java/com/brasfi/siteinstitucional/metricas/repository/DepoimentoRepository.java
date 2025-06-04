package com.brasfi.siteinstitucional.metricas.repository;

import com.brasfi.siteinstitucional.metricas.entity.Depoimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {

    List<Depoimento> findByAtivoTrueOrderByOrdemAsc();

    List<Depoimento> findByDestaqueAndAtivoTrueOrderByOrdemAsc(boolean destaque);

    List<Depoimento> findByAnoAndAtivoTrueOrderByOrdemAsc(Integer ano);

    List<Depoimento> findByOrganizacaoAndAtivoTrueOrderByOrdemAsc(String organizacao);

    @Query("SELECT DISTINCT d.ano FROM Depoimento d WHERE d.ativo = true ORDER BY d.ano DESC")
    List<Integer> findDistinctAnosAtivos();

    @Query("SELECT d FROM Depoimento d WHERE " +
            "(:ano IS NULL OR d.ano = :ano) AND " +
            "(:organizacao IS NULL OR d.organizacao LIKE %:organizacao%) AND " +
            "(:destaque IS NULL OR d.destaque = :destaque) AND " +
            "(:ativo IS NULL OR d.ativo = :ativo) " +
            "ORDER BY d.ordem ASC")
    Page<Depoimento> buscarComFiltros(
            @Param("ano") Integer ano,
            @Param("organizacao") String organizacao,
            @Param("destaque") Boolean destaque,
            @Param("ativo") Boolean ativo,
            Pageable pageable);
}