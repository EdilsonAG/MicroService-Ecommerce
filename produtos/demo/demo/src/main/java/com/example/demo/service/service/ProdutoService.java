package com.example.demo.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.ProdutoRepository;
import com.example.demo.service.model.Produto;

@Service
public class ProdutoService {
    
    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.listarProdutos();
    }

}
