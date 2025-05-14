package com.brasfi.siteinstitucional.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pilares {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
