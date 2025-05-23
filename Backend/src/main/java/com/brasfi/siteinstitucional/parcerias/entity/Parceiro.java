package com.brasfi.siteinstitucional.parcerias.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parceiros")
public class Parceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nomeOrganizacao;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "site_url")
    private String siteUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_parceria", nullable = false)
    private TipoParceria tipoParceria; // MEMBRO_ASSOCIADO, PARCEIRO_ESTRATEGICO, APOIADOR

    @Column(name = "setor_atuacao", length = 100)
    private String setorAtuacao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "parceiro_categorias",
            joinColumns = @JoinColumn(name = "parceiro_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<CategoriaParceiro> categorias;

    @Column(name = "data_inicio_parceria")
    private LocalDateTime dataInicioParceria;

    @Column(nullable = false)
    private boolean visivel = true;

    @Column(nullable = false)
    private boolean ativo = true; // Para soft delete ou desativação temporária

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