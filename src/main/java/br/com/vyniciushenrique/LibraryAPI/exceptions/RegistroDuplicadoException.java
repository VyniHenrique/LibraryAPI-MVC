package br.com.vyniciushenrique.LibraryAPI.exceptions;

public class RegistroDuplicadoException extends RuntimeException{

    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
