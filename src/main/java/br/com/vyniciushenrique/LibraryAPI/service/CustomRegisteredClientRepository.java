package br.com.vyniciushenrique.LibraryAPI.service;

import br.com.vyniciushenrique.LibraryAPI.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;

    @Override
    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {

        Optional<Client> clientOptional = clientService.obterPorClientId(clientId);


        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();


            return RegisteredClient
                    .withId(client.getId().toString())
                    .clientId(client.getClientId())
                    .clientSecret(client.getClientSecret())
                    .redirectUri(client.getRedirectURI())
                    .scope(client.getScope())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .tokenSettings(tokenSettings)
                    .clientSettings(clientSettings)
                    .build();
        }

        return null;
    }
}
