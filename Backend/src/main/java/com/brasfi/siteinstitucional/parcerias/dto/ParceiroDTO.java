package com.brasfi.siteinstitucional.parcerias.dto;

import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParceiroDTO {
    private Long id;

    @NotBlank(message = "Nome da organização é obrigatório")
    @Size(max = 200)
    private String nomeOrganizacao;

    private String descricao;

    @URL(message = "URL do logo inválida")
    private String logoUrl;

    @URL(message = "URL do site inválida")
    private String siteUrl;

    @NotNull(message = "Tipo de parceria é obrigatório")
    private TipoParceria tipoParceria;

    @Size(max = 100)
    private String setorAtuacao;

    private Set<Long> categoriaIds; // Para vincular categorias existentes

    private LocalDateTime dataInicioParceria;

    private boolean visivel = true;
    private boolean ativo = true;
}