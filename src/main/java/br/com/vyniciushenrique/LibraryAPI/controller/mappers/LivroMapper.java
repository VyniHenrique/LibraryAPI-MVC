package br.com.vyniciushenrique.LibraryAPI.controller.mappers;

import br.com.vyniciushenrique.LibraryAPI.controller.dto.CadastroLivroDTO;
import br.com.vyniciushenrique.LibraryAPI.controller.dto.PesquisaLivroDTO;
import br.com.vyniciushenrique.LibraryAPI.model.Livro;
import br.com.vyniciushenrique.LibraryAPI.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    protected AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract PesquisaLivroDTO toDTO(Livro livro);
}
