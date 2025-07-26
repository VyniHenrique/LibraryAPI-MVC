package br.com.vyniciushenrique.LibraryAPI.controller.common;

import br.com.vyniciushenrique.LibraryAPI.controller.dto.ErroCampo;
import br.com.vyniciushenrique.LibraryAPI.controller.dto.ErroResposta;
import br.com.vyniciushenrique.LibraryAPI.exceptions.CampoInvalidoExeption;
import br.com.vyniciushenrique.LibraryAPI.exceptions.OperacaoNaoPermitidaException;
import br.com.vyniciushenrique.LibraryAPI.exceptions.RegistroDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e){

        log.error("Erro de validação {}", e.getMessage());

        List<FieldError> fieldError = e.getFieldErrors();
        List<ErroCampo> listaErroCampos = fieldError.stream().map(fe ->
                new ErroCampo(
                        fe.getField(),
                        fe.getDefaultMessage()))
                .collect(Collectors.toList());



        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),  "erro de validação", listaErroCampos);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoExeption.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoExeption(CampoInvalidoExeption e){
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "erro de validação" ,
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccesDeniedException(AccessDeniedException e){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso negado", List.of());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleUsernameNotFoundException(UsernameNotFoundException e){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleExeptionNaoTratada(RuntimeException e){

        log.error("Erro inesperado", e);

        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado", List.of());
    }

}
