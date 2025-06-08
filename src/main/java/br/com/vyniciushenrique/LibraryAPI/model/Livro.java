package br.com.vyniciushenrique.LibraryAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 100, nullable = false)
    private String isbn;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private GeneroLivro genero;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "preco", precision = 5, scale = 2)
    private BigDecimal preco;

}
