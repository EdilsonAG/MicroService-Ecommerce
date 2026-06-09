package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.model.Produto;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    
    @GetMapping
    public List<Produto> listarProduto(){
        System.out.println("deu boa galera");
        return new ArrayList<Produto>();
    }

    @PostMapping
    public void teste2(){
        System.out.println("ihaa");
    }
}
