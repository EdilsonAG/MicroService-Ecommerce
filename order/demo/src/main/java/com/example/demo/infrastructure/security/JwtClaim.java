package com.example.demo.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtClaim {
     public JwtAuthenticationToken getAuthentication(){
        return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId(){
        String usuarioId = getAuthentication()
            .getToken()
            .getClaim("usuario_id");

    return usuarioId != null ? Long.valueOf(usuarioId) : null;
    }
}
