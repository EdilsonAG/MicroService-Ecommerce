package com.example.demo.service.strategy.broker;

import java.util.Map;

public class StrategyBroker {
    
    private Map<String, BrokerInterface> broker;

    public StrategyBroker(Map<String, BrokerInterface> broker){
        this.broker = broker;
    }

    public void enviarMensagem(String broker, BrokerInterfaceMarkup brokerInterfaceMarkup ){
        BrokerInterface brokerInterface = this.broker.get(broker);
        brokerInterface.enviarMensagemAoBroker(brokerInterfaceMarkup);
    }
}
