package com.brasfi.siteinstitucional.metricas.repository;

import com.brasfi.siteinstitucional.metricas.entity.HistoricoMetrica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoMetricaRepository extends JpaRepository<HistoricoMetrica, Long> {

    Page<HistoricoMetrica> findByMetricaIdOrderByDataAlteracaoDesc(Long metricaId, Pageable pageable);

    Page<HistoricoMetrica> findByUsuarioIdOrderByDataAlteracaoDesc(Long usuarioId, Pageable pageable);
}
