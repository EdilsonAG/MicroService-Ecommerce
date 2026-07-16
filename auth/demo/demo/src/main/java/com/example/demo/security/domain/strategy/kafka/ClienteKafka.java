package com.example.demo.security.domain.strategy.kafka;

public class ClienteKafka implements InterfaceBroker{
    
    private String _id;
    private String _nome;
    private String _email;
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String get_nome() {
        return _nome;
    }
    public void set_nome(String _nome) {
        this._nome = _nome;
    }
    public String get_email() {
        return _email;
    }
    public void set_email(String _email) {
        this._email = _email;
    }

}
