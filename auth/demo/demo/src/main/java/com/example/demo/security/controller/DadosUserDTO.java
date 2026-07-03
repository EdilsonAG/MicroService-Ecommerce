package com.example.demo.security.controller;

public class DadosUserDTO {
    
    public record LoginRequest(String email, String password) {}

    public record RegisterRequest(String email, String pass, String name){}
    public record RegisterRequest2(String email, String pass, String name){}
    public record RegisterRequest3(String email, String pass, String name){}
    public record RegisterRequest4(String email, String pass, String name){}

}
