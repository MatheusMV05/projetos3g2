package com.brasfi.siteinstitucional.admin.analytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventos_analytics")
public class EventoAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String categoria;

    private String acao;

    private String rotulo;

    private Integer valor;

    @Column(nullable = false)
    private String ipUsuario;

    private String userAgent;

    private String paginaReferencia;

    private String paginaAtual;

    private Long usuarioId;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @PrePersist
    protected void onCreate() {
        dataHora = LocalDateTime.now();
    }
}