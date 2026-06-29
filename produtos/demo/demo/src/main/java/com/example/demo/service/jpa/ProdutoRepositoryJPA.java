package com.example.demo.service.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.service.entity.ProdutoEntity;
import com.example.demo.service.service.ProdutoRepositoryQueries;

public interface ProdutoRepositoryJPA extends JpaRepository<ProdutoEntity,Long> , ProdutoRepositoryQueries{
    
}
