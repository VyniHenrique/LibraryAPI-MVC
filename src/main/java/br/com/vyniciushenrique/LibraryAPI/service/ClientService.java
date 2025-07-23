package br.com.vyniciushenrique.LibraryAPI.service;

import br.com.vyniciushenrique.LibraryAPI.model.Client;
import br.com.vyniciushenrique.LibraryAPI.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        return clientRepository.save(client);
    }

    public Optional<Client> obterPorClientId(String clientId){
        return clientRepository.findByClientId(clientId);
    }
}