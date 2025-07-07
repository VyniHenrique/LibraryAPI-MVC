package br.com.vyniciushenrique.LibraryAPI.validator;

import br.com.vyniciushenrique.LibraryAPI.exceptions.CampoInvalidoExeption;
import br.com.vyniciushenrique.LibraryAPI.exceptions.RegistroDuplicadoException;
import br.com.vyniciushenrique.LibraryAPI.model.Livro;
import br.com.vyniciushenrique.LibraryAPI.repository.LivroRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private LivroRepository livroRepository;

    public LivroValidator(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    public void validar(Livro livro){

        if (existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("Já existe um livro com esse isbn");
        }

        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoExeption("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}
