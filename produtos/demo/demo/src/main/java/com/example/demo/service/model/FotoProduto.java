package com.example.demo.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

public class FotoProduto {
    
 
    private Long id;

    private String url;

 
    private Produto produto;
}
