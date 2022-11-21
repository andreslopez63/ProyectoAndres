package com.example.proyectoandres.ui.pets;

public class Pet {

    public Pet(){};

    public Pet(String nombrePet, String imagenPet) {
        this.nombrePet = nombrePet;
        this.imagenPet = imagenPet;
    }

    String nombrePet, imagenPet;

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
}
