package com.example.demo.security.domain.strategy.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

 import com.example.demo.security.domain.strategy.StrategyBrokerInterface;

@Component
public class KafkaService implements StrategyBrokerInterface {

    @Autowired
    private KafkaTemplate<String,ClienteKafka> kafkaTemplate;
    @Override
    public void enviarMensagemAoBroker(InterfaceBroker interfaceBroker) {
        if(interfaceBroker instanceof ClienteKafka cliente){

            kafkaTemplate.send("user.created",cliente);
        }
    }
    
}
