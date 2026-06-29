package com.example.demo.infraestructure;

import org.springframework.stereotype.Repository;

import com.example.demo.service.model.FotoProduto;
import com.example.demo.service.service.ProdutoRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public FotoProduto save(FotoProduto fotoProduto) {
        return manager.merge(fotoProduto);
    }

    @Override
    @Transactional
    public void delete(FotoProduto foto) {
        manager.remove(foto);
    }
}
