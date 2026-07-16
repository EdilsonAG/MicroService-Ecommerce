package com.example.demo.security.domain.service;

import java.util.Map;

import com.example.demo.security.domain.strategy.StrategyBrokerInterface;
import com.example.demo.security.domain.strategy.kafka.InterfaceBroker;

public class BrokerStrategy {

    private Map<String,StrategyBrokerInterface> broker;
    
    public BrokerStrategy(Map<String,StrategyBrokerInterface> broker){
        this.broker = broker;
    }

    public void enviarMensagemAoBroker(String broker, InterfaceBroker interfaceBroker ){
        StrategyBrokerInterface brokerEncontrado = this.broker.get(broker);
        brokerEncontrado.enviarMensagemAoBroker(interfaceBroker);
    }
}
