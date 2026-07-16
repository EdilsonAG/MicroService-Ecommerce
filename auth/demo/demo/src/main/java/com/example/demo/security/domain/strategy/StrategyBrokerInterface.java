package com.example.demo.security.domain.strategy;

import com.example.demo.security.domain.strategy.kafka.InterfaceBroker;

public interface StrategyBrokerInterface {
    
    public void enviarMensagemAoBroker(InterfaceBroker interfaceBroker);
}
