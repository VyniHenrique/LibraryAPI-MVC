package br.com.vyniciushenrique.LibraryAPI.validator;

import br.com.vyniciushenrique.LibraryAPI.exceptions.RegistroDuplicadoException;
import br.com.vyniciushenrique.LibraryAPI.model.Autor;
import br.com.vyniciushenrique.LibraryAPI.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){

        if (existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Já existe um autor cadastrado!");
        }
    }

    public boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = autorRepository.
                findByNomeAndDataNascimentoAndNacionalidade
                        (
                            autor.getNome(),
                            autor.getDataNascimento(),
                            autor.getNacionalidade()
                        );

        if (autor.getId() == null){
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }

}
