package com.brasfi.siteinstitucional.parcerias.dto;

import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoParceriaCreateDTO {

    @NotBlank(message = "Nome da organização é obrigatório")
    @Size(max = 200)
    private String nomeOrganizacao;

    @NotBlank(message = "Nome do contato é obrigatório")
    @Size(max = 100)
    private String nomeContato;

    @NotBlank(message = "Email de contato é obrigatório")
    @Email(message = "Email de contato inválido")
    @Size(max = 100)
    private String emailContato;

    @Size(max = 20)
    private String telefoneContato;

    @NotNull(message = "Tipo de solicitação é obrigatório")
    private TipoParceria tipoSolicitacao;

    @NotBlank(message = "A mensagem/proposta é obrigatória")
    @Size(min = 50, max = 5000, message = "Mensagem deve ter entre 50 e 5000 caracteres")
    private String mensagemProposta;

}