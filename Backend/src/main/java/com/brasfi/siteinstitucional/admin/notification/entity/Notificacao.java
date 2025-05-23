package com.brasfi.siteinstitucional.admin.notification.entity;

import com.brasfi.siteinstitucional.auth.entity.Usuario;
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
@Table(name = "notificacoes")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(nullable = false)
    private String tipo; // INFO, WARNING, ERROR

    private String acao; // URL ou ação a ser executada ao clicar

    @Column(nullable = false)
    private boolean lida = false;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataLeitura;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}