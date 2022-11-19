package com.example.proyectoandres.ui.home;

public class Articulo {
    private String imagen;
    private String titulo;

    public Articulo(){};

    public Articulo(String imagen, String titulo) {
        this.imagen = imagen;
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
