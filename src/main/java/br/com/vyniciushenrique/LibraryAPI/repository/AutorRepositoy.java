package br.com.vyniciushenrique.LibraryAPI.repository;

import br.com.vyniciushenrique.LibraryAPI.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepositoy extends JpaRepository<Autor, UUID> {

}
