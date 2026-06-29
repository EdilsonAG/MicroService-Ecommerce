package com.example.demo.service.service;

import com.example.demo.service.model.FotoProduto;

public interface ProdutoRepositoryQueries {
    FotoProduto save(FotoProduto fotoProduto);
    void delete(FotoProduto foto);
}
