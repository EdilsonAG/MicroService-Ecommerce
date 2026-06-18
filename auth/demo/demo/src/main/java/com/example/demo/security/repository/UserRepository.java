package com.example.demo.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.security.domain.entity.ClienteEntity;

public interface UserRepository extends JpaRepository<ClienteEntity, Long> {

    @Query("SELECT c FROM ClienteEntity c " +
            "LEFT JOIN FETCH c.grupos g " +
            "LEFT JOIN FETCH g.permissoes " +
            "WHERE c.email = :email")
    Optional<ClienteEntity> findByEmail(String email);
}
