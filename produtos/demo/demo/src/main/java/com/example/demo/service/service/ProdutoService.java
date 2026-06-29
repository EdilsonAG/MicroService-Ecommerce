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
import com.example.demo.service.model.ProdutoKafka;
import com.example.demo.service.strategy.broker.BrokerInterfaceMarkup;
import com.example.demo.service.strategy.broker.StrategyBroker;
import com.example.demo.service.strategy.storage.StrategyStorage;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    
    private ProdutoRepository produtoRepository;
    private StrategyStorage strategyStorage;
    private StrategyBroker strategyBroker;

    public ProdutoService(ProdutoRepository produtoRepository, StrategyStorage strategyStorage, StrategyBroker strategyBroker){
        this.produtoRepository = produtoRepository;
        this.strategyStorage = strategyStorage;
        this.strategyBroker = strategyBroker;
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.listarProdutos();
    }


    public Produto produtoById(Long id){
        return produtoRepository.produtoById(id);
    }

    @Transactional
    public Produto cadastrarProduto(Produto produtoRequests,List<MultipartFile> files){


        Produto produto = produtoRepository.salvar(produtoRequests);

        NovaFoto novaFoto = new NovaFoto();
        novaFoto.setProduto(produto);
        novaFoto.setFiles(files);

        strategyStorage.armazenar("Local", novaFoto);

        ProdutoKafka produtoKafka = new ProdutoKafka(); 
        produtoKafka.setId(produto.getId());
        produtoKafka.setDescricao(produto.getDescricao());
        produtoKafka.setNome(produto.getNome());

        System.out.println(produto.getId());
        System.out.println(produto.getNome());
        System.out.println(produto.getDescricao());
        strategyBroker.enviarMensagem("KafkaMensagem", produtoKafka);

        return produto;
    }

}
