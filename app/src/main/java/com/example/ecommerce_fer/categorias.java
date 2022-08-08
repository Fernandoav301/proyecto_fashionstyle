package com.example.ecommerce_fer;

public class categorias {
    private String categoria;
    private String ftcategoria;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFtcategoria() {
        return ftcategoria;
    }

    public void setFtcategoria(String ftcategoria) {
        this.ftcategoria = ftcategoria;
    }

    public categorias(String categoria, String ftcategoria){

        this.categoria = categoria;
        this.ftcategoria = ftcategoria;
    }



}
