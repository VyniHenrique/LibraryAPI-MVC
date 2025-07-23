package br.com.vyniciushenrique.LibraryAPI.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientDTO(

        @NotBlank(message = "O campo 'clientId' é obrigatorio")
        String clientId,

        @NotBlank(message = "O campo 'clientSecret' é obrigatorio")
        String clientSecret,

        @NotBlank(message = "O campo 'redirectURI' é obrigatorio")
        String redirectURI,

        String scope
) {
}
