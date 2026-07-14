package com.example.demo.service.strategy.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;


import com.example.demo.service.model.ProdutoKafka;

@Component("KafkaMensagem")
public class KafkaMensagem implements BrokerInterface {

    @Autowired
    private KafkaTemplate<String, Object> kafka;

    @Override
    public void enviarMensagemAoBroker(BrokerInterfaceMarkup brokerInterfaceMarkup) {

        if (brokerInterfaceMarkup instanceof ProdutoKafka produto) {

            kafka.send("product.updated", produto);
            System.out.println(produto.getNome());
        }

    }

    @Override
    public void deletarMensagemAoBroker(BrokerInterfaceMarkup brokerInterfaceMarkup) {
         if (brokerInterfaceMarkup instanceof ProdutoKafka produto) {

             kafka.send("product.updated", produto.getId().toString(), null);
         }
    }

}
