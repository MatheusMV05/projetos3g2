package com.brasfi.siteinstitucional.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private String nome;
    private Set<String> perfis; // Set of role names
    private boolean ativo;
    private LocalDateTime ultimoLogin;
    private LocalDateTime dataCriacao;
}