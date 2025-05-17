package com.brasfi.siteinstitucional.admin.analytics.repository;

import com.brasfi.siteinstitucional.admin.analytics.dto.DadosAgrupadosDTO;
import com.brasfi.siteinstitucional.admin.analytics.entity.EventoAnalytics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoAnalyticsRepository extends JpaRepository<EventoAnalytics, Long> {

    Page<EventoAnalytics> findByTipo(String tipo, Pageable pageable);

    Page<EventoAnalytics> findByCategoria(String categoria, Pageable pageable);

    Page<EventoAnalytics> findByDataHoraBetween(
            LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    @Query("SELECT new com.brasfi.siteinstitucional.admin.analytics.dto.DadosAgrupadosDTO(" +
            "e.paginaAtual, COUNT(e)) FROM EventoAnalytics e " +
            "WHERE e.tipo = 'PAGEVIEW' " +
            "AND (:inicio IS NULL OR e.dataHora >= :inicio) " +
            "AND (:fim IS NULL OR e.dataHora <= :fim) " +
            "GROUP BY e.paginaAtual " +
            "ORDER BY COUNT(e) DESC")
    List<DadosAgrupadosDTO> getPaginasMaisVisitadas(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable);

    @Query("SELECT new com.brasfi.siteinstitucional.admin.analytics.dto.DadosAgrupadosDTO(" +
            "e.paginaReferencia, COUNT(e)) FROM EventoAnalytics e " +
            "WHERE e.tipo = 'PAGEVIEW' " +
            "AND e.paginaReferencia IS NOT NULL " +
            "AND (:inicio IS NULL OR e.dataHora >= :inicio) " +
            "AND (:fim IS NULL OR e.dataHora <= :fim) " +
            "GROUP BY e.paginaReferencia " +
            "ORDER BY COUNT(e) DESC")
    List<DadosAgrupadosDTO> getReferenciasPopulares(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable);

    @Query("SELECT COUNT(DISTINCT e.ipUsuario) FROM EventoAnalytics e " +
            "WHERE e.tipo = 'PAGEVIEW' " +
            "AND (:inicio IS NULL OR e.dataHora >= :inicio) " +
            "AND (:fim IS NULL OR e.dataHora <= :fim)")
    Long getUsuariosUnicos(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
}