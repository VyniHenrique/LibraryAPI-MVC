package br.com.vyniciushenrique.LibraryAPI.controller.dto;

import br.com.vyniciushenrique.LibraryAPI.model.GeneroLivro;
import br.com.vyniciushenrique.LibraryAPI.model.Livro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Livro")
public record CadastroLivroDTO(
        @NotBlank(message = "O campo 'isbn' é obrigatorio")
        @ISBN
        String isbn,

        @NotBlank(message = "O campo 'titulo'é obrigatorio")
        String titulo,

        @NotNull(message = "O campo 'dataPublicacao' é obrigatorio")
        @PastOrPresent(message = "Não pode ser uma data futura")
        LocalDate dataPublicacao,


        GeneroLivro genero,

        BigDecimal preco,

        @NotNull(message = "O campo 'idAutor' é obrigatorio")
        UUID idAutor) {


}
