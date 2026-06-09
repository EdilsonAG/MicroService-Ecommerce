package com.example.demo.service.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Produto cadastrarProduto(Produto produtoRequests,List<MultipartFile> files){

        for (MultipartFile multipartFile : files) {

            String nomeArquivo = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
            Path caminho = Paths.get("uploads/" + nomeArquivo);
            try {
                
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

}
