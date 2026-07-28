package com.example.demo.domain.order.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.application.dto.order.CarEvent;

@Service
public class ConsumerCarrinhoCreateOrder {
    


    @KafkaListener(topics = "car-checkout", groupId = "group-order-car",
                   containerFactory = "kafkaListenerContainerFactory")
    public void consumir(String mensagem) {
        System.out.println(mensagem);
        System.out.println(mensagem);
    }
}
