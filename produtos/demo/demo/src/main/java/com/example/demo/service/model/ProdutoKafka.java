package com.example.demo.service.model;

import com.example.demo.service.strategy.broker.BrokerInterfaceMarkup;

public class ProdutoKafka implements BrokerInterfaceMarkup{
    
     private Long id;
    private String nome;
    private String descricao;

    

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
