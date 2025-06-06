package com.brasfi.siteinstitucional.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString(exclude = {"foto", "senha"}) // Não incluir dados sensíveis no toString
public class AdminUserCreateRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String senha;

    private MultipartFile foto;

    // Método para verificar se há foto
    public boolean temFoto() {
        return foto != null && !foto.isEmpty();
    }

    // Método para obter o tamanho da foto
    public long getTamanhoFoto() {
        return foto != null ? foto.getSize() : 0;
    }

    // Método para obter o tipo da foto
    public String getTipoFoto() {
        return foto != null ? foto.getContentType() : null;
    }
}