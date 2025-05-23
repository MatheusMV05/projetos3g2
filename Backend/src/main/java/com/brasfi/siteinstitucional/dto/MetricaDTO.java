// Pertence à etapa: Desenvolver API CRUD para métricas
package com.brasfi.siteinstitucional.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetricaDTO {
    private Long id;

    @NotBlank(message = "O slug é obrigatório")
    private String slug;

    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String unidade;
    private LocalDate dataReferencia;
}
