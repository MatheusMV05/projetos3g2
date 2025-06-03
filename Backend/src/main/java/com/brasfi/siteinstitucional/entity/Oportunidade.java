package com.brasfi.siteinstitucional.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "oportunidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Oportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "data_inicio")
    private LocalDate dataInicio; // Usando data_inicio para período_inicio

    @Column(name = "data_fim")
    private LocalDate dataFim;    // Usando data_fim para período_fim

    @Column(columnDefinition = "TEXT")
    private String requisitos;

    @Column(name = "como_participar", columnDefinition = "TEXT")
    private String comoParticipar;


}