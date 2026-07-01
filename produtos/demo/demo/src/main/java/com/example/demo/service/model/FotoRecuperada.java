package com.example.demo.service.model;

import java.io.InputStream;

import lombok.Builder;

@Builder
public class FotoRecuperada {
    private InputStream inputStream;
        private String url;

        public InputStream getInputStream() {
            return inputStream;
        }
        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public boolean temUrl(){
            return url != null;
        }
        public boolean temInputStream(){
            return inputStream != null;
        }
}
