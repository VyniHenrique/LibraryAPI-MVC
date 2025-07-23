package br.com.vyniciushenrique.LibraryAPI.controller.mappers;

import br.com.vyniciushenrique.LibraryAPI.controller.dto.ClientDTO;
import br.com.vyniciushenrique.LibraryAPI.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO dto);
}
