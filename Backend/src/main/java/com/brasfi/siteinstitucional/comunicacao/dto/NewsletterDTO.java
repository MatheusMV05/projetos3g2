package com.brasfi.siteinstitucional.comunicacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterDTO {

    private Long id;

    @NotBlank(message = "Assunto é obrigatório")
    @Size(min = 3, max = 200, message = "Assunto deve ter entre 3 e 200 caracteres")
    private String assunto;

    @NotBlank(message = "Conteúdo é obrigatório")
    private String conteudo;

    private Boolean enviarImediatamente;
}