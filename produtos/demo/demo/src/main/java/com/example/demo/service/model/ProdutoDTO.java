package com.example.demo.service.model;

import java.util.List;

public class ProdutoDTO {
    private String nome;
    private List<String> fotos; // Base64
    // getters/setters
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<String> getFotos() {
        return fotos;
    }
    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}