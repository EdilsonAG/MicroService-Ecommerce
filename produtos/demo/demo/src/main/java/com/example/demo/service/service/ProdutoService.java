package com.example.demo.service.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repository.ProdutoRepository;
import com.example.demo.service.model.FotoProduto;
import com.example.demo.service.model.NovaFoto;
import com.example.demo.service.model.Produto;
import com.example.demo.service.strategy.storage.StrategyStorage;

@Service
public class ProdutoService {
    
    private ProdutoRepository produtoRepository;
    private StrategyStorage strategyStorage;

    public ProdutoService(ProdutoRepository produtoRepository, StrategyStorage strategyStorage){
        this.produtoRepository = produtoRepository;
        this.strategyStorage = strategyStorage;
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.listarProdutos();
    }

    public Produto cadastrarProduto(Produto produtoRequests,List<MultipartFile> files){


        Produto produto = produtoRepository.salvar(produtoRequests);

        NovaFoto novaFoto = new NovaFoto();
        novaFoto.setProduto(produto);
        novaFoto.setFiles(files);

        strategyStorage.armazenar("Local", novaFoto);

        return produto;
    }

}
