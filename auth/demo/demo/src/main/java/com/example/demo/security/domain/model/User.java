package com.example.demo.security.domain.model;

import com.example.demo.security.controller.DadosUserDTO;

public record User(String nome, String email, String senha) {

    public static User of(DadosUserDTO.RegisterRequest dto) {
        return new User(dto.name(), dto.email(), dto.pass());
    }
}