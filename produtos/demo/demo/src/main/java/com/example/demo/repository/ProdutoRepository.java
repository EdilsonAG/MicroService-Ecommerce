package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.stereotype.Repository;

import com.example.demo.service.entity.ProdutoEntity;
import com.example.demo.service.jpa.ProdutoRepositoryJPA;
import com.example.demo.service.model.Produto;

@Repository
public class ProdutoRepository {

    private ProdutoRepositoryJPA produtoRepositoryJPA;

    public ProdutoRepository(ProdutoRepositoryJPA produtoRepositoryJPA) {
        this.produtoRepositoryJPA = produtoRepositoryJPA;
    }

    public List<Produto> listarProdutos() {
        List<ProdutoEntity> produtosEntities = produtoRepositoryJPA.findAll();

        List<Produto> produtos = new ArrayList<>();

        produtosEntities.stream().forEach(p -> {
            Produto produto = new Produto();
            produto.setId(p.getId());
            produto.setDescricao(p.getDescricao());
            produto.setNome(p.getNome());
            produtos.add(produto);
        });

        return produtos;

    }

    public Produto salvar(Produto produto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setDescricao(produto.getDescricao());
        produtoEntity.setNome(produto.getNome());
        ProdutoEntity produtoEntitySalvo = new ProdutoEntity();
        try {

             produtoEntitySalvo = produtoRepositoryJPA.save(produtoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Produto produtoSalvo = new Produto();
        produtoSalvo.setId(produtoEntitySalvo.getId());
        produtoSalvo.setDescricao(produtoEntitySalvo.getDescricao());
        produtoSalvo.setNome(produtoEntitySalvo.getNome());

        return produtoSalvo;

    }
}
