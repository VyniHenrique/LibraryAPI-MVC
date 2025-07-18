package br.com.vyniciushenrique.LibraryAPI.sercurity;

import br.com.vyniciushenrique.LibraryAPI.model.Usuario;
import br.com.vyniciushenrique.LibraryAPI.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "123";


    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        Optional<Usuario> usuarioOptional = usuarioService.obterPorEmail(email);

        Usuario usuario = new Usuario();

        if (usuarioOptional.isPresent()){
            usuario = usuarioOptional.get();
        }else {
            usuario.setLogin(obterLoginAtravesDoEmail(email));
            usuario.setEmail(email);
            usuario.setSenha(SENHA_PADRAO);
            usuario.setRoles(List.of("OPERADOR"));
            usuarioService.salvar(usuario);
        }

        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String obterLoginAtravesDoEmail(String email){
        return email.substring(0, email.indexOf("@"));
    }
}
