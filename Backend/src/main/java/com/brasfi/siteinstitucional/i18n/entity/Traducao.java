package com.brasfi.siteinstitucional.i18n.entity;

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
@Table(name = "traducoes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"chave", "idioma"})
})
public class Traducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String chave; // Ex: "home.titulo", "parceiros.descricao_pagina"

    @Column(nullable = false, length = 10) // ex: "pt-BR", "en-US", "es-ES"
    private String idioma;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String valor;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}