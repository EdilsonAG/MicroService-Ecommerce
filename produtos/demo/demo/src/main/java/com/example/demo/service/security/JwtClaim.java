package com.example.demo.service.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
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
