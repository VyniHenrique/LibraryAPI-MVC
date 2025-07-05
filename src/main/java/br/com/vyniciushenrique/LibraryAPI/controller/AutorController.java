package br.com.vyniciushenrique.LibraryAPI.controller;


import br.com.vyniciushenrique.LibraryAPI.controller.dto.AutorDTO;
import br.com.vyniciushenrique.LibraryAPI.controller.dto.ErroResposta;
import br.com.vyniciushenrique.LibraryAPI.controller.mappers.AutorMapper;
import br.com.vyniciushenrique.LibraryAPI.exceptions.RegistroDuplicadoException;
import br.com.vyniciushenrique.LibraryAPI.model.Autor;
import br.com.vyniciushenrique.LibraryAPI.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    public AutorController(AutorService service, AutorMapper mapper) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar (@RequestBody @Valid AutorDTO dto){

            Autor autor = mapper.toEntity(dto);
            service.salvar(autor);
            URI location = gerarHeaderLocation(autor.getId());
            return ResponseEntity.created(location).build();

    }


    @GetMapping("{id}")
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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id ){

        var idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            service.deletar(autor);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
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

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar (@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO){

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
