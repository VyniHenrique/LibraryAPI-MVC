package br.com.vyniciushenrique.LibraryAPI.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(

        @NotBlank(message = "O campo 'nome' é obrigatorio")
        @Size(min = 3,max = 40, message = "Limite de caracteres execedido")
        String nome,

        @NotNull(message = "O campo 'data de nascimento' é obrigatorio")
        @Past(message = "Data de nascimento inválida")
        LocalDate dataNascimento,



        @NotBlank(message = "O campo 'nacionalidade' é obrigatorio")
        @Size(max = 50, min = 4, message = "Limite de caracteres excedido")
        String nacionalidade,

        UUID id) {

}
