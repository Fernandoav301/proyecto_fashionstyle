package com.example.ecommerce_fer;

public class productos {

    private String nombre;
    private Integer precio;
    private String descripcion;
    private String foto;

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {this.nombre = nombre;}

    public Integer getPrecio() { return precio; }

    public void setPrecio(Integer precio) {this.precio = precio;}

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getFoto() { return foto; }

    public void setFoto(String foto) {this.foto = foto;}

    public productos(String nombre, Integer precio, String descripcion, String foto){

        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.foto = foto;
    }


}
