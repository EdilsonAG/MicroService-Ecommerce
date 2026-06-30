package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.entity.FotoProdutoEntity;
import com.example.demo.service.entity.ProdutoEntity;
import com.example.demo.service.jpa.FotoProdutoRepository;
import com.example.demo.service.jpa.ProdutoRepositoryJPA;
import com.example.demo.service.model.Produto;
import com.example.demo.service.model.ProdutoDTO;
import com.example.demo.service.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepositoryJPA produtoRepositoryJPA;

    @Autowired
    private FotoProdutoRepository fotoProdutoRepository;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> listarProduto() {
        return produtoService.listarProdutos();
    }

    @GetMapping("/{id}")
    public Produto produtoById(@PathVariable Long id) {
        return produtoService.produtoById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Produto criarProduto(@RequestPart("files") List<MultipartFile> files,
            @RequestPart("produtoRequests") Produto produtoRequests) {
        System.out.println("PRODUTO CHEGOU");
        return produtoService.cadastrarProduto(produtoRequests, files);
    }

   
}
