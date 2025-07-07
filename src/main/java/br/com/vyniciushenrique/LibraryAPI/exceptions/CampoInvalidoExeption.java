package br.com.vyniciushenrique.LibraryAPI.exceptions;

import lombok.Getter;

public class CampoInvalidoExeption extends RuntimeException {

  @Getter
  private String campo;

  public CampoInvalidoExeption(String campo, String mensagem) {
    super(mensagem);
    this.campo = campo;
  }
}
