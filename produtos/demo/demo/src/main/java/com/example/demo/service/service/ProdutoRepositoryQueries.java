package com.example.demo.service.service;

import com.example.demo.service.entity.FotoProdutoEntity;
import com.example.demo.service.model.FotoProduto;

public interface ProdutoRepositoryQueries {
    FotoProdutoEntity save(FotoProdutoEntity fotoProduto);
    void delete(FotoProduto foto);
}
