package com.brasfi.siteinstitucional.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SobreDTO {
    private Long id;

    @NotBlank(message = "O slug é obrigatório")
    private String slug;

    //esses atributos aqui embaixo são atributos feitos pelo Claude
    //Deixei aqui pra analisarmos se iremos adicionar eles nas entidades
    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String conteudo;

    private String equipe;

    private String localizacao;

    private String contato;
}