package br.com.vyniciushenrique.LibraryAPI.repository;

import br.com.vyniciushenrique.LibraryAPI.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepositoy repositoy;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setName("123User");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1980, Month.AUGUST, 30));

        var autorSalvo = repositoy.save(autor);

        System.out.println("Autor salvo: " + autorSalvo);

    }

    @Test
    public void atualizarTeste(){
        var id = UUID.fromString("5b618451-b747-4964-86b5-b35e222171ba");

        Optional<Autor> possivelUsurario = repositoy.findById(id);

        if(possivelUsurario.isPresent()){

            Autor usuarioEncontrado = possivelUsurario.get();
            System.out.println("Dados do autor");
            System.out.println(usuarioEncontrado);

            usuarioEncontrado.setDataNascimento(LocalDate.of(1920, Month.MARCH, 15));

            repositoy.save(usuarioEncontrado);

        }


    }

    @Test
    public void listarUsuarios(){

        List<Autor> lista = repositoy.findAll();

        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repositoy.count());
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("5b618451-b747-4964-86b5-b35e222171ba");

        Optional<Autor> possivelUsuario = repositoy.findById(id);

        if (possivelUsuario.isPresent()){
            Autor usuarioEncontrado = possivelUsuario.get();
            System.out.println("Dados do usuario deletado: \n" + usuarioEncontrado);

            repositoy.deleteById(id);
        }
    }
}
