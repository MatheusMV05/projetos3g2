package com.brasfi.siteinstitucional.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Sobre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
