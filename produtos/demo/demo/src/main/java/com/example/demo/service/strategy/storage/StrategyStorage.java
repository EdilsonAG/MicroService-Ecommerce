package com.example.demo.service.strategy.storage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.service.model.FotoProduto;
import com.example.demo.service.model.NovaFoto;
 

@Component
public class StrategyStorage {

    private Map<String, FotoStorage> fotoStorageMap;

    public StrategyStorage(Map<String, FotoStorage> fotoStorageMap) {
        this.fotoStorageMap = fotoStorageMap;
    }

    public void armazenar(String storage, NovaFoto novaFoto) {
        FotoStorage fotoStorage = fotoStorageMap.get(storage);
        fotoStorage.armazenar(novaFoto);
    }

    // nomeArquivo agora é passado corretamente
    // public FotoRecuperada recuperar(String storage, String nomeArquivo) {
    //     FotoStorage fotoStorage = fotoStorageMap.get(storage);
    //     return fotoStorage.recuperar(nomeArquivo);
    // }

    // public List<String> listar(String storage) {
    //     FotoStorage fotoStorage = fotoStorageMap.get(storage);
    //     return fotoStorage.listar();
    // }
}