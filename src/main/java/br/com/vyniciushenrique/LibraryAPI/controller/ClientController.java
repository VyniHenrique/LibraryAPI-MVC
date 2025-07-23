package br.com.vyniciushenrique.LibraryAPI.controller;

import br.com.vyniciushenrique.LibraryAPI.controller.dto.ClientDTO;
import br.com.vyniciushenrique.LibraryAPI.controller.mappers.ClientMapper;
import br.com.vyniciushenrique.LibraryAPI.model.Client;
import br.com.vyniciushenrique.LibraryAPI.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody ClientDTO clientDTO){
        Client client = clientMapper.toEntity(clientDTO);
        clientService.salvar(client);
    }

}
