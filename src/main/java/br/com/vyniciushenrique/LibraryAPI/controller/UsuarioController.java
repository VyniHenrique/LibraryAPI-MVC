package br.com.vyniciushenrique.LibraryAPI.controller;

import br.com.vyniciushenrique.LibraryAPI.controller.dto.UsuarioDTO;
import br.com.vyniciushenrique.LibraryAPI.controller.mappers.UsuarioMapper;
import br.com.vyniciushenrique.LibraryAPI.model.Usuario;
import br.com.vyniciushenrique.LibraryAPI.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@Tag(name = "Usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper){
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar(@RequestBody @Valid UsuarioDTO usuarioDTO){

        var usuario = usuarioMapper.toEntity(usuarioDTO);
        usuarioService.salvar(usuario);
    }
}
