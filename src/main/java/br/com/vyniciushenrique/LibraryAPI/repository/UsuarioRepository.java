package br.com.vyniciushenrique.LibraryAPI.repository;

import br.com.vyniciushenrique.LibraryAPI.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByEmail(String email);
}
