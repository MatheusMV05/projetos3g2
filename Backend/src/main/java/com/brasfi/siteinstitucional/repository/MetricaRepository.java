package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Metrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface MetricaRepository extends JpaRepository<Metrica, Long> {

    @Query("SELECT m FROM Metrica m WHERE " +
            "(:nome IS NULL OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:descricao IS NULL OR LOWER(m.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))) AND " +
            "(:valorMinimo IS NULL OR m.valor >= :valorMinimo) AND " +
            "(:valorMaximo IS NULL OR m.valor <= :valorMaximo) AND " +
            "(:unidade IS NULL OR m.unidade = :unidade) AND " +
            "(:dataInicio IS NULL OR m.dataReferencia >= :dataInicio) AND " +
            "(:dataFim IS NULL OR m.dataReferencia <= :dataFim)")
    List<Metrica> buscarMetricasComFiltros(
            @Param("nome") String nome,
            @Param("descricao") String descricao,
            @Param("valorMinimo") BigDecimal valorMinimo,
            @Param("valorMaximo") BigDecimal valorMaximo,
            @Param("unidade") String unidade,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query("SELECT m.unidade as unidade, COUNT(m) as quantidade, AVG(m.valor) as media, " +
            "MAX(m.valor) as maximo, MIN(m.valor) as minimo " +
            "FROM Metrica m GROUP BY m.unidade")
    List<Map<String, Object>> buscarEstatisticasPorUnidade();

    @Query("SELECT FUNCTION('YEAR', m.dataReferencia) as ano, " +
            "FUNCTION('MONTH', m.dataReferencia) as mes, " +
            "AVG(m.valor) as media " +
            "FROM Metrica m " +
            "GROUP BY FUNCTION('YEAR', m.dataReferencia), FUNCTION('MONTH', m.dataReferencia) " +
            "ORDER BY ano, mes")
    List<Map<String, Object>> buscarEvolucaoMensal();

    @Query("SELECT COUNT(m) as total, AVG(m.valor) as media, MAX(m.valor) as maximo, MIN(m.valor) as minimo " +
            "FROM Metrica m")
    Map<String, Object> buscarEstatisticasGerais();
}