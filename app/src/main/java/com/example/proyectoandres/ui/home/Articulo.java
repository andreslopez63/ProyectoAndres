package com.example.proyectoandres.ui.home;

public class Articulo {
    private String imagen;
    private String titulo;
    private String usuario;

    public Articulo(){};

    public Articulo(String imagen, String titulo, String usuario) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.usuario = usuario;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
