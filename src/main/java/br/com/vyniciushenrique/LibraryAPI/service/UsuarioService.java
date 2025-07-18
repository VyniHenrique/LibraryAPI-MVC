package br.com.vyniciushenrique.LibraryAPI.service;

import br.com.vyniciushenrique.LibraryAPI.model.Usuario;
import br.com.vyniciushenrique.LibraryAPI.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(passwordEncoder.encode(senha));
        repository.save(usuario);
    }

    public Optional<Usuario> obterPorLogin(String login){

        Optional<Usuario> usuarioOptional = repository.findByLogin(login);
        if (usuarioOptional.isEmpty()){
            throw new UsernameNotFoundException("Usuário e/ou senha incorretos");
        }

        return repository.findByLogin(login);
    }

    public Optional<Usuario> obterPorEmail(String email){
        return repository.findByEmail(email);
    }


}
