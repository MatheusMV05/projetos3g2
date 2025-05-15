package com.brasfi.siteinstitucional.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaDTO {
    private Long id;

    @NotBlank(message = "O slug é obrigatório")
    private String slug;

    //esses atributos aqui embaixo são atributos feitos pelo Claude
    //Deixei aqui pra analisarmos se iremos adicionar eles nas entidades
    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String conteudo;

    private String dataFundacao;

    private String marcos;
}