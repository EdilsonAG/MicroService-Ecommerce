package com.example.demo.service.strategy.storage;

import com.example.demo.service.model.NovaFoto;

public interface FotoStorage {
    public abstract void armazenar(NovaFoto NovaFoto);
}
