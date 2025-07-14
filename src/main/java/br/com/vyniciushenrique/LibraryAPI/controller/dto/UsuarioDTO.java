package br.com.vyniciushenrique.LibraryAPI.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "O campo 'login' é obrigatório")
        @Size(min = 3, max = 30)
        String login,

        @NotBlank(message = "O campo 'senha' é obrigatório")
        @Size(min = 3, max = 30)
        String senha,

        List<String> roles) {
}
