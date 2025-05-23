// Pertence à etapa: Criar endpoints de depoimentos (opcional)
package com.brasfi.siteinstitucional.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepoimentoDTO {
    private Long id;

    @NotBlank(message = "O slug é obrigatório")
    private String slug;

    private String autor;
    private String mensagem;
    private String cargo;
}
