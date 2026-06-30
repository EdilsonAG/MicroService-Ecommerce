package com.example.demo.service.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.service.entity.FotoProdutoEntity;
import com.example.demo.service.entity.ProdutoEntity;

public interface  FotoProdutoRepository  extends JpaRepository<FotoProdutoEntity,Long>{
    List<FotoProdutoEntity> findByProduto(ProdutoEntity produto);
    @Query("SELECT f FROM FotoProdutoEntity f JOIN FETCH f.produto")
List<FotoProdutoEntity> findAllComProduto();
}
