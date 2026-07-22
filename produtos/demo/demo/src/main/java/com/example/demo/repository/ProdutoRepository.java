package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.stereotype.Repository;

import com.example.demo.service.entity.FotoProdutoEntity;
import com.example.demo.service.entity.ProdutoEntity;
import com.example.demo.service.jpa.ProdutoRepositoryJPA;
import com.example.demo.service.model.FotoProduto;
import com.example.demo.service.model.Produto;

@Repository
public class ProdutoRepository {

    private ProdutoRepositoryJPA produtoRepositoryJPA;

    public ProdutoRepository(ProdutoRepositoryJPA produtoRepositoryJPA) {
        this.produtoRepositoryJPA = produtoRepositoryJPA;
    }

    public void deletarProduto(Long id){
        produtoRepositoryJPA.deleteById(id);
    }

    public Produto produtoById(Long id){
        ProdutoEntity produtoEntity = produtoRepositoryJPA.findById(id).orElseThrow( ()->{ throw new IllegalArgumentException(); });
        Produto produto = new Produto();
        produto.setId(produtoEntity.getId());
        produto.setDescricao(produtoEntity.getDescricao());
        produto.setNome(produtoEntity.getNome());
        return produto;
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

    public FotoProduto salvarFoto(FotoProduto fotoProduto ){

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setDescricao(fotoProduto.getProduto().getDescricao());
        produtoEntity.setId(fotoProduto.getProduto().getId());
        produtoEntity.setNome(fotoProduto.getProduto().getNome());

        FotoProdutoEntity fotoProdutoEntity = new FotoProdutoEntity();
        fotoProdutoEntity.setProduto(produtoEntity);
        fotoProdutoEntity.setUrl(fotoProduto.getUrl());

        produtoRepositoryJPA.save(fotoProdutoEntity);

        return fotoProduto;

    }

    public Produto salvar(Produto produto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setDescricao(produto.getDescricao());
        produtoEntity.setNome(produto.getNome());
        produtoEntity.setPreco(produto.getPreco());
         
        
            ProdutoEntity produtoEntitySalvo = produtoRepositoryJPA.save(produtoEntity);
        
        Produto produtoSalvo = new Produto();
        produtoSalvo.setId(produtoEntitySalvo.getId());
        produtoSalvo.setDescricao(produtoEntitySalvo.getDescricao());
        produtoSalvo.setNome(produtoEntitySalvo.getNome());

        return produtoSalvo;

    }
}
