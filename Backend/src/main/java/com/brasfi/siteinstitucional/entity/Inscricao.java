package com.brasfi.siteinstitucional.entity; // Ajuste o pacote

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscricoes")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Muitas inscrições para um evento
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(nullable = false)
    private String email;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDateTime dataInscricao;

    @Enumerated(EnumType.STRING) // Armazena o enum como String no DB
    @Column(nullable = false)
    private InscricaoStatus status;

    // Construtor padrão
    public Inscricao() {
        this.dataInscricao = LocalDateTime.now(); // Define a data/hora da inscrição automaticamente
        this.status = InscricaoStatus.PENDENTE; // Status inicial
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(LocalDateTime dataInscricao) { this.dataInscricao = dataInscricao; }
    public InscricaoStatus getStatus() { return status; }
    public void setStatus(InscricaoStatus status) { this.status = status; }
}