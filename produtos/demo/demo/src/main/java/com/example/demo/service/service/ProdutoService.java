package com.example.demo.service.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repository.ProdutoRepository;
import com.example.demo.service.jpa.FotoProdutoRepository;
import com.example.demo.service.model.FotoProduto;
import com.example.demo.service.model.NovaFoto;
import com.example.demo.service.model.Produto;
import com.example.demo.service.model.ProdutoKafka;
import com.example.demo.service.model.ProdutoRequest;
import com.example.demo.service.model.ProdutoResponse;
import com.example.demo.service.strategy.broker.BrokerInterfaceMarkup;
import com.example.demo.service.strategy.broker.StrategyBroker;
import com.example.demo.service.strategy.storage.StoragePropeties;
import com.example.demo.service.strategy.storage.StrategyStorage;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private StrategyStorage strategyStorage;
    private StrategyBroker strategyBroker;
    private FotoProdutoRepository fotoProdutoRepository;

    @Autowired
    private StoragePropeties storagePropeties;

    public ProdutoService(ProdutoRepository produtoRepository, StrategyStorage strategyStorage,
            StrategyBroker strategyBroker, FotoProdutoRepository fotoProdutoRepository) {
        this.produtoRepository = produtoRepository;
        this.strategyStorage = strategyStorage;
        this.strategyBroker = strategyBroker;
        this.fotoProdutoRepository = fotoProdutoRepository;
    }


    public void deletarProdutoById(Long id){
        produtoRepository.deletarProduto(id);
    }

    public List<ProdutoResponse> listarProdutos() {
        //List<Produto> produto = produtoRepository.listarProdutos();
        return fotoProdutoRepository.buscarTodosComFoto();
        // List<ProdutoResponse> produtoResponses = new ArrayList<>();
        // produto.stream().forEach(item -> {
        //     ProdutoResponse produtoResponse = new ProdutoResponse();
        //     produtoResponse.setDescricao(item.getDescricao());
        //     produtoResponse.setId(item.getId());
        //     produtoResponse.setNome(item.getNome());
        //     produtoResponse.setPreco(item.getPreco());
 
        //     produtoResponses.add(produtoResponse);
        // } );
 
    }

    public Produto produtoById(Long id) {
        return produtoRepository.produtoById(id);
    }

    private Path getArquivoPath(String nomeArquivo) {

        return storagePropeties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }

    @Transactional
    public Produto cadastrarProduto(Produto produtoRequests, List<MultipartFile> files) {
        ProdutoKafka produtoKafka = new ProdutoKafka();
        produtoKafka.setNome(produtoRequests.getNome());


        Produto produto = produtoRepository.salvar(produtoRequests);

        NovaFoto novaFoto = new NovaFoto();

        FotoProduto fotoProduto = new FotoProduto();

        for (MultipartFile file : files) {
            try {
                String originalName = file.getOriginalFilename();
                String orginalFileNameRandom = gerarNomeArquivo(originalName);
                produto.setNome(orginalFileNameRandom);
                produtoRequests.setNome(orginalFileNameRandom);
                // Path arquivoPath = getArquivoPath(file.getOriginalFilename());
                // FileCopyUtils.copy(file.getInputStream(),
                // Files.newOutputStream(arquivoPath));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        novaFoto.setFiles(files);

        
        fotoProduto.setProduto(produto);
 
        novaFoto.setProduto(produto);
     
        fotoProduto.setUrl(produtoRequests.getNome());

        produtoRepository.salvarFoto(fotoProduto);

        strategyStorage.armazenar("Local", novaFoto);

        produtoKafka.setId(produto.getId());
        produtoKafka.setDescricao(produto.getDescricao());

        System.out.println(produto.getId());
        System.out.println(produto.getNome());
        System.out.println(produto.getDescricao());
        strategyBroker.enviarMensagem("KafkaMensagem", produtoKafka);

        return produto;
    }

    private String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

}
