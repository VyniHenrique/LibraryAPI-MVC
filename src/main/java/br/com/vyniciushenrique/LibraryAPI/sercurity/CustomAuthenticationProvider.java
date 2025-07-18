package br.com.vyniciushenrique.LibraryAPI.sercurity;

import br.com.vyniciushenrique.LibraryAPI.model.Usuario;
import br.com.vyniciushenrique.LibraryAPI.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Optional<Usuario> usuarioOptional = usuarioService.obterPorLogin(login);

        Usuario usuarioEncontrado = usuarioOptional.get();

        String senhaCriptografada = usuarioEncontrado.getSenha();

        boolean senhasBatem = passwordEncoder.matches(senhaDigitada, senhaCriptografada);

        if (senhasBatem){
            return new CustomAuthentication(usuarioEncontrado);
        }

        throw new UsernameNotFoundException("Usuario e/ou senha incorretos");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
