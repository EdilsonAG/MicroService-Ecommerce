package com.example.demo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.security.domain.entity.ClienteEntity;

public interface UserRepository extends JpaRepository<ClienteEntity, Long> {
    
}
