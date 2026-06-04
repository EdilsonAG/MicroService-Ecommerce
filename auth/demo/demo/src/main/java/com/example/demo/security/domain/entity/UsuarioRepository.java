package com.example.demo.security.domain.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<ClienteEntity,Long>
{

    Optional<ClienteEntity> findByEmail(String email);
}
