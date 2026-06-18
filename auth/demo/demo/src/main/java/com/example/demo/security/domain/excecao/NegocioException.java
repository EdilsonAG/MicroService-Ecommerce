package com.example.demo.security.domain.excecao;

public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public NegocioException(String mensagem) {
        super(mensagem);
    }

}
