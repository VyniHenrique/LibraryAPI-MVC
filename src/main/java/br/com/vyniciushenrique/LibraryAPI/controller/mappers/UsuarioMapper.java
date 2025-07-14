package br.com.vyniciushenrique.LibraryAPI.controller.mappers;

import br.com.vyniciushenrique.LibraryAPI.controller.dto.UsuarioDTO;
import br.com.vyniciushenrique.LibraryAPI.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO usuarioDTO);

    UsuarioDTO toDTO(Usuario usuario);


}
