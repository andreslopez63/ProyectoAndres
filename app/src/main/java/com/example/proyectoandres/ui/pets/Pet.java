package com.example.proyectoandres.ui.pets;

public class Pet {

    public Pet() {
    }

    public Pet(String nombrePet, String imagenPet, String usuario, String idmascota) {
        this.nombrePet = nombrePet;
        this.imagenPet = imagenPet;
        this.usuario = usuario;
        this.idmascota = idmascota;
    }


    public String getNombrePet() {
        return nombrePet;
    }

    public void setNombrePet(String nombrePet) {
        this.nombrePet = nombrePet;
    }

    public String getImagenPet() {
        return imagenPet;
    }

    public void setImagenPet(String imagenPet) {
        this.imagenPet = imagenPet;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdmascota() {
        return idmascota;
    }

    public void setIdmascota(String idmascota) {
        this.idmascota = idmascota;
    }

    String nombrePet, imagenPet, usuario, idmascota;

}