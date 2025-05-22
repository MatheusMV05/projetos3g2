package com.brasfi.siteinstitucional.admin.notification.repository;

import com.brasfi.siteinstitucional.admin.notification.entity.Notificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    Page<Notificacao> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<Notificacao> findByUsuarioIdAndLida(Long usuarioId, boolean lida, Pageable pageable);

    List<Notificacao> findTop5ByUsuarioIdAndLidaOrderByDataCriacaoDesc(Long usuarioId, boolean lida);

    @Query("SELECT COUNT(n) FROM Notificacao n WHERE n.usuario.id = :usuarioId AND n.lida = false")
    long countNaoLidasByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT n FROM Notificacao n WHERE " +
            "n.usuario.id = :usuarioId AND " +
            "(:lida IS NULL OR n.lida = :lida) AND " +
            "(:tipo IS NULL OR n.tipo = :tipo) AND " +
            "(:inicio IS NULL OR n.dataCriacao >= :inicio) AND " +
            "(:fim IS NULL OR n.dataCriacao <= :fim)")
    Page<Notificacao> buscarComFiltros(
            @Param("usuarioId") Long usuarioId,
            @Param("lida") Boolean lida,
            @Param("tipo") String tipo,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable);
}