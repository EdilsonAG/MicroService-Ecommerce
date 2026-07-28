package com.example.demo.application.dto.order;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.application.dto.car.ItemCarrinhoEvent;
import com.example.demo.application.dto.user.UserEvent;

public class CarEvent {
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    private UserEvent user;
    private List<ItemCarrinhoEvent> itensCarrinho = new ArrayList<>();
 
    public UserEvent getUser() {
        return user;
    }
    public void setUser(UserEvent user) {
        this.user = user;
    }
    public List<ItemCarrinhoEvent> getItensCarrinho() {
        return itensCarrinho;
    }
    public void setItensCarrinho(List<ItemCarrinhoEvent> itensCarrinho) {
        this.itensCarrinho = itensCarrinho;
    }
}
