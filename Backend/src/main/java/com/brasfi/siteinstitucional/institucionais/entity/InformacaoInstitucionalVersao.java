package com.brasfi.siteinstitucional.institucionais.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "informacoes_institucionais_versoes")
public class InformacaoInstitucionalVersao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "informacao_id", nullable = false)
    private InformacaoInstitucional informacao;

    @Column(nullable = false)
    private Integer versao;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String valorAnterior;

    @Column(columnDefinition = "TEXT")
    private String valorNovo;

    private String descricaoAlteracao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private com.brasfi.siteinstitucional.auth.entity.Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataAlteracao;

    @PrePersist
    protected void onCreate() {
        dataAlteracao = LocalDateTime.now();
    }
}