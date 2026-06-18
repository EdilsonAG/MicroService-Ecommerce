package com.example.demo.security.domain.excecao;

public class EntidadeNaoEncontrada extends NegocioException {

    public EntidadeNaoEncontrada(String mensagem) {
        super(mensagem);
    }
    
}
