package com.brasfi.siteinstitucional.metricas.entity;

import com.brasfi.siteinstitucional.auth.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historico_metricas")
public class HistoricoMetrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metrica_id", nullable = false)
    private MetricaImpacto metrica;

    @Column(nullable = false)
    private BigDecimal valorAnterior;

    @Column(nullable = false)
    private BigDecimal valorNovo;

    private String descricaoAlteracao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataAlteracao;

    @PrePersist
    protected void onCreate() {
        dataAlteracao = LocalDateTime.now();
    }
}