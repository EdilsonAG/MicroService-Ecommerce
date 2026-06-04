package com.example.demo.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.security.domain.entity.AuthUser;
import com.example.demo.security.domain.entity.ClienteEntity;
import com.example.demo.security.domain.entity.UsuarioRepository;

@Service
public class JpaUserDetailService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public JpaUserDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ClienteEntity usuarioEntity = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));

       return new User(usuarioEntity.getEmail(),usuarioEntity.getSenha(),this.getAuthorities(usuarioEntity));          
       // return new AuthUser(usuarioEntity,  this.getAuthorities(usuarioEntity));
    }

    private Collection<GrantedAuthority> getAuthorities(ClienteEntity usuarioEntity) {

        return usuarioEntity.getGrupos().stream().flatMap(grupo -> grupo.getPermissoes().stream())
                .map(permissao -> new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase()))
                .collect(Collectors.toSet());
        // new SimpleGrantedAuthority("CONSULTAR_COZINHAS");

        /*
         * Sem stream:
         * 
         * for (Grupo g : usuarioEntity.getGrupos()) {
         * System.out.println(g.getNome());
         * }
         * 
         * 
         * Com stream:
         * 
         * usuarioEntity.getGrupos()
         * .stream()
         * .forEach(g -> System.out.println(g.getNome()));
         */

        // stream basicamente te ajuda a percorrer uma list ou set

    }
}
