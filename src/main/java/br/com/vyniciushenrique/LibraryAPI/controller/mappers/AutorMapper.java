package br.com.vyniciushenrique.LibraryAPI.controller.mappers;

import br.com.vyniciushenrique.LibraryAPI.controller.dto.AutorDTO;
import br.com.vyniciushenrique.LibraryAPI.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
