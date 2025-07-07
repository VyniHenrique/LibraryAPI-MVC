package br.com.vyniciushenrique.LibraryAPI.service;

import br.com.vyniciushenrique.LibraryAPI.model.GeneroLivro;
import br.com.vyniciushenrique.LibraryAPI.model.Livro;
import br.com.vyniciushenrique.LibraryAPI.repository.LivroRepository;
import br.com.vyniciushenrique.LibraryAPI.validator.LivroValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.vyniciushenrique.LibraryAPI.repository.specs.LivroSpecs.*;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;


    LivroService (LivroRepository livroRepository, LivroValidator livroValidator){
        this.livroRepository = livroRepository;
        this.livroValidator = livroValidator;

    }

    public Livro salvar(Livro livro){
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisar(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){

        Specification<Livro> specs = (root, query, cb) -> cb.conjunction();

        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs);
    }


    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessario que o livro já esteja salvo na base");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
