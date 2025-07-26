package br.com.vyniciushenrique.LibraryAPI.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(name = "Usuário")
public record UsuarioDTO(
        @NotBlank(message = "O campo 'login' é obrigatório")
        @Size(min = 3, max = 30)
        String login,

        @NotBlank(message = "O campo 'senha' é obrigatório")
        @Size(min = 3, max = 30)
        String senha,

        @NotBlank(message = "O campo 'email' é obrigotório")
        @Size(min = 12)
        @Email(message = "inválido")
        String email,

        List<String> roles) {
}
