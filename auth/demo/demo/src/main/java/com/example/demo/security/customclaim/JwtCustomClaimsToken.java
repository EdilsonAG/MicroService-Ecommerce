package com.example.demo.security.customclaim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import com.example.demo.security.domain.entity.ClienteEntity;
import com.example.demo.security.domain.entity.UsuarioRepository;

public class JwtCustomClaimsToken implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Autowired
    UsuarioRepository usuarioRepository;

    // public JwtCustomClaimsToken(UsuarioRepository usuarioRepository) {
    // this.usuarioRepository = usuarioRepository;
    // }

    @Override
    public void customize(JwtEncodingContext context) {

        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            var principal = context.getPrincipal().getPrincipal();

            if (principal instanceof User user) {

                ClienteEntity usuarioEntity = usuarioRepository.findByEmail(user.getUsername())

                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        "Usuário não encontrado com o email: " + user.getUsername()));
                //context.getClaims().claim("usuario_id", usuarioEntity.getId());

                context.getClaims()
                        .claim("usuario_id", String.valueOf(usuarioEntity.getId()));
            }
        }
    }

}
