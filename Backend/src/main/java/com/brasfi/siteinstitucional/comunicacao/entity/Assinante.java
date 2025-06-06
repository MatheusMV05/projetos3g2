package com.brasfi.siteinstitucional.comunicacao.entity;

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
@Table(name = "assinantes_newsletter", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Assinante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean ativo = true;

    private String nome;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    private LocalDateTime dataDesativacao;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }
}