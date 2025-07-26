package br.com.vyniciushenrique.LibraryAPI.controller;

import br.com.vyniciushenrique.LibraryAPI.controller.dto.CadastroLivroDTO;
import br.com.vyniciushenrique.LibraryAPI.controller.dto.PesquisaLivroDTO;
import br.com.vyniciushenrique.LibraryAPI.controller.mappers.LivroMapper;
import br.com.vyniciushenrique.LibraryAPI.model.GeneroLivro;
import br.com.vyniciushenrique.LibraryAPI.model.Livro;
import br.com.vyniciushenrique.LibraryAPI.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@Tag(name = "Livros")
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    LivroController(LivroService service, LivroMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(
            summary = "Salvar",
            description = "Cadastra um livro no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado")
    }
    )
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastroLivroDTO livroDTO) {

            Livro livro = mapper.toEntity(livroDTO);
            service.salvar(livro);

            URI location = gerarHeaderLocation(livro.getId());

            return ResponseEntity.created(location).build();

    }



    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(
            summary = "Obter detalhes",
            description = "Recebe detalhes de um livro"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    }
    )
    public ResponseEntity<PesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){


        return service.obterPorId(UUID.fromString(id)).map( (livro) -> {
            PesquisaLivroDTO dto = mapper.toDTO(livro);
            return ResponseEntity.ok(dto);

        }).orElseGet( () -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(
            summary = "Detelar",
            description = "Deleta um livro do banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    }
    )
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id)).map(livro -> {
            service.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(
            summary = "Pesquisar",
            description = "Pesquisa um livro seguindo parâmetros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso")
    }
    )
    public ResponseEntity<Page<PesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,

            @RequestParam(value = "titulo", required = false)
            String titulo,

            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,

            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,

            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,

            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,

            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ){
        Page<Livro> paginaResultado = service.pesquisar(
                isbn,
                titulo,
                nomeAutor,
                genero,
                anoPublicacao,
                pagina,
                tamanhoPagina);

        Page<PesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(
            summary = "Atualizar",
            description = "Atualiza um livro do banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    }
    )
    public ResponseEntity<Object> atualizar(
            @Valid
            @PathVariable("id")
            String id,

            @Valid
            @RequestBody
            CadastroLivroDTO livroDTO){

            return service.obterPorId(UUID.fromString(id)).map( livro -> {
                Livro entityAux = mapper.toEntity(livroDTO);
                livro.setDataPublicacao(entityAux.getDataPublicacao());
                livro.setIsbn(entityAux.getIsbn());
                livro.setPreco(entityAux.getPreco());
                livro.setGenero(entityAux.getGenero());
                livro.setTitulo(entityAux.getTitulo());
                livro.setAutor(entityAux.getAutor());
                service.atualizar(livro);
                return ResponseEntity.noContent().build();
            }).orElseGet( () -> ResponseEntity.notFound().build());

    }

}
