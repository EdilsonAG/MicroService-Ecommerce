package com.example.demo.service.strategy.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.model.FotoRecuperada;
import com.example.demo.service.model.NovaFoto;

@Component("Local")
public class LocalStorage implements FotoStorage {

    @Autowired
    private StoragePropeties storagePropeties;

    // @Override
    // public void armazenar(NovaFoto novaFoto) {

    // for (MultipartFile files : novaFoto.getFiles()) {
    // try {
    // String caminho = files.getOriginalFilename();
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    // }
    // }

    @Override
    public void armazenar(NovaFoto foto) {
        System.out.println("nome que chegou aqui \n\n");
        System.out.println(foto.getProduto().getNome());

        for (MultipartFile file : foto.getFiles()) {
            try {
                Path arquivoPath = getArquivoPath(foto.getProduto().getNome());
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(arquivoPath));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }

    private Path getArquivoPathAleatorio(String nomeArquivo) {
       String gerarNomeAleatorio = UUID.randomUUID() + nomeArquivo;
        return storagePropeties.getLocal().getDiretorioFotos().resolve(Path.of(gerarNomeAleatorio));
    }

     private Path getArquivoPath(String nomeArquivo) {
       
        return storagePropeties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
 
    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);

            FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
                                        .inputStream(Files.newInputStream(arquivoPath))
                                        .build();

            return fotoRecuperada;
            //return Files.newInputStream(arquivoPath);
        } catch (Exception e) {
            // throw new Exception("Não foi possível recuperar arquivo.", e);
        }
        return null;
    }
}
