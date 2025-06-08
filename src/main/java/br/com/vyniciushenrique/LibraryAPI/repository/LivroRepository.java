package br.com.vyniciushenrique.LibraryAPI.repository;

import br.com.vyniciushenrique.LibraryAPI.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

}
