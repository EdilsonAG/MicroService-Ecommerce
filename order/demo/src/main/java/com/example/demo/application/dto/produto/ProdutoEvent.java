package com.example.demo.application.dto.produto;

public class ProdutoEvent {
    private Long id;
    private String descricaoProduto;
    private String nomeProduto;
    private Long preco;
    private String url;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescricaoProduto() {
        return descricaoProduto;
    }
    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public Long getPreco() {
        return preco;
    }
    public void setPreco(Long preco) {
        this.preco = preco;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
