package com.example.demo.application.dto.car;

import com.example.demo.application.dto.produto.ProdutoEvent;

public class ItemCarrinhoEvent {
    private String id;   
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    private Long quantidade;
    private ProdutoEvent produto;
 
    public Long getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
    public ProdutoEvent getProduto() {
        return produto;
    }
    public void setProduto(ProdutoEvent produto) {
        this.produto = produto;
    }
}
