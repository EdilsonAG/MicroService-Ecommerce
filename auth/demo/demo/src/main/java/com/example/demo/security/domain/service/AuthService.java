package com.example.demo.security.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;


import org.springframework.kafka.core.KafkaTemplate;

import com.example.demo.security.controller.DadosUserDTO;
import com.example.demo.security.domain.entity.ClienteEntity;
import com.example.demo.security.domain.excecao.NegocioException;
import com.example.demo.security.domain.model.User;
import com.example.demo.security.domain.strategy.kafka.ClienteKafka;
import com.example.demo.security.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private KafkaTemplate<String,ClienteEntity> kafkaTemplate;

    @Autowired
    private PasswordEncoder  passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrokerStrategy brokerStrategy;


    public void registrarUsuario(DadosUserDTO.RegisterRequest registerRequest) {
        User user = User.of(registerRequest);

        User userEncoded = new User(user.nome(),user.email(),passwordEncoder.encode(user.senha()));
        
        
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setEmail(userEncoded.email());
        clienteEntity.setNome(userEncoded.nome());
        clienteEntity.setSenha(userEncoded.senha());
        
        Optional<ClienteEntity> clienteEncontrado = userRepository.findByEmail(clienteEntity.getEmail());
        System.out.println();
        if(clienteEncontrado.isPresent()){
            throw new NegocioException("Email ja cadastrado");
        }
//        userRepository.findByEmail();
        ClienteEntity clienteSalvo =    userRepository.save(clienteEntity);

        ClienteKafka clienteKafka = new ClienteKafka();
        clienteKafka.set_id(clienteSalvo.getId());
        clienteKafka.set_email(clienteSalvo.getEmail());
        clienteKafka.set_nome(clienteSalvo.getNome());

        brokerStrategy.enviarMensagemAoBroker("KAFKA", clienteKafka, "user.created");
       // kafkaTemplate.send("user.created", clienteEntity);
    }
}
