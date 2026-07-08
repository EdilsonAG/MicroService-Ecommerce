package com.example.demo.service.model;

public class ProdutoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private String url;
    private Long preco;

    

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Long getPreco() {
        return preco;
    }
    public void setPreco(Long preco) {
        this.preco = preco;
    }
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
