package com.brasfi.siteinstitucional.parcerias.entity;

import com.brasfi.siteinstitucional.auth.entity.Usuario;
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
@Table(name = "solicitacoes_parceria")
public class SolicitacaoParceria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_organizacao", nullable = false, length = 200)
    private String nomeOrganizacao;

    @Column(name = "nome_contato", nullable = false, length = 100)
    private String nomeContato;

    @Column(nullable = false, length = 100)
    private String emailContato;

    @Column(length = 20)
    private String telefoneContato;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_solicitacao", nullable = false)
    private TipoParceria tipoSolicitacao; // Que tipo de parceria/associação está buscando

    @Column(columnDefinition = "TEXT")
    private String mensagemProposta; // Detalhes da proposta ou motivação

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status; // PENDENTE, APROVADA, REJEITADA

    @Column(name = "data_solicitacao", nullable = false, updatable = false)
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_ultima_atualizacao_status")
    private LocalDateTime dataUltimaAtualizacaoStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_responsavel_id")
    private Usuario adminResponsavel; // Admin que analisou/respondeu

    @Column(columnDefinition = "TEXT")
    private String observacoesAdmin;

    @PrePersist
    protected void onCreate() {
        dataSolicitacao = LocalDateTime.now();
        status = StatusSolicitacao.PENDENTE;
    }
}