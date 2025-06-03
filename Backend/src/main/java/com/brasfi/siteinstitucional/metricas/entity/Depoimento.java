package com.brasfi.siteinstitucional.metricas.entity;

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
@Table(name = "depoimentos")
public class Depoimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String cargo;

    @Column(nullable = false, length = 200)
    private String organizacao;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "iniciativa_relacionada")
    private String iniciativaRelacionada;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Integer ordem = 0;

    @Column(nullable = false)
    private boolean destaque = false;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_inclusao", nullable = false, updatable = false)
    private LocalDateTime dataInclusao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataInclusao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}