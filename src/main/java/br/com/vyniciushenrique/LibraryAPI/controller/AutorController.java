package br.com.vyniciushenrique.LibraryAPI.controller;


import br.com.vyniciushenrique.LibraryAPI.controller.dto.AutorDTO;
import br.com.vyniciushenrique.LibraryAPI.controller.mappers.AutorMapper;
import br.com.vyniciushenrique.LibraryAPI.model.Autor;
import br.com.vyniciushenrique.LibraryAPI.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@Tag(name = "Autores")
@Slf4j
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;


    public AutorController(AutorService service, AutorMapper mapper) {
        this.mapper = mapper;
        this.service = service;
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    @Operation(
            summary = "Salvar",
            description = "Cadastrar novo autor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado")
    }
    )
    public ResponseEntity<Void> salvar (@RequestBody @Valid AutorDTO dto){

        log.info("Cadasrando novo autor: {}", dto.nome());

        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();

    }



    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @GetMapping("{id}")
    @Operation(
            summary = "Buscar por ID",
            description = "Buscar por um ID cadastrado no banco"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    }
    )
    public ResponseEntity<AutorDTO> buscarPorId (@PathVariable("id") String id){

        var idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO autorDTO = mapper.toDTO(autor);
            return ResponseEntity.ok(autorDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("{id}")
    @Operation(
            summary = "Deletar",
            description = "Deletar autor do banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    }
    )
    public ResponseEntity<Void> deletar(@PathVariable("id") String id ){

        log.info("Deletando um autor de ID: {}", id);

        var idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            service.deletar(autor);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }


    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @GetMapping
    @Operation(
            summary = "Pesquisar",
            description = "Pesquisar autor por parâmetros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
    }
    )
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){

        List<Autor> resultado = service.pesquisarByExample(nome, nacionalidade);

        List<AutorDTO> lista = resultado.stream().map(autor -> new AutorDTO(
                                                    autor.getNome(),
                                                    autor.getDataNascimento(),
                                                    autor.getNacionalidade(),
                                                    autor.getId())).collect(Collectors.toList());


        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("{id}")
    @Operation(
            summary = "Atualizar",
            description = "Atualiza dados do autor no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    }
    )
    public ResponseEntity<Void> atualizar (@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO){

        log.info("Atualizando dados do autor de ID {}", id);

        var idAutor = UUID.fromString(id);


        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isPresent()){

            var autor = autorOptional.get();
            autor.setNome(autorDTO.nome());
            autor.setNacionalidade(autorDTO.nacionalidade());
            autor.setDataNascimento(autorDTO.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
