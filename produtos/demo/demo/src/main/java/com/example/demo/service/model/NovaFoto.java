package com.example.demo.service.model;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class NovaFoto {

    private Produto produto;
    private List<MultipartFile> files;
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public List<MultipartFile> getFiles() {
        return files;
    }
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    // private String nomeArquivo;
    // private String contentType;
    // private InputStream inputStream;
    // public String getNomeArquivo() {
    //     return nomeArquivo;
    // }
    // public void setNomeArquivo(String nomeArquivo) {
    //     this.nomeArquivo = nomeArquivo;
    // }
    // public String getContentType() {
    //     return contentType;
    // }
    // public void setContentType(String contentType) {
    //     this.contentType = contentType;
    // }
    // public InputStream getInputStream() {
    //     return inputStream;
    // }
    // public void setInputStream(InputStream inputStream) {
    //     this.inputStream = inputStream;
    // }

}
