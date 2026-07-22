package com.example.demo.service.strategy.broker;

public interface BrokerInterface {
 
    public void enviarMensagemAoBroker(BrokerInterfaceMarkup brokerInterfaceMarkup);
    public void deletarMensagemAoBroker(BrokerInterfaceMarkup brokerInterfaceMarkup);
}
