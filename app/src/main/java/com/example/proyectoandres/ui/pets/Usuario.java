package com.example.proyectoandres.ui.pets;

public class Usuario {


    String Apellidos, Nombre;
    int Telefono;
    Boolean Veterinario;
    String idusuario;

    public Usuario() {
    }

    public Usuario(String apellidos, String nombre, int telefono, Boolean veterinario, String idusuario) {
        Apellidos = apellidos;
        Nombre = nombre;
        Telefono = telefono;
        Veterinario = veterinario;
        this.idusuario = idusuario;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }

    public Boolean getVeterinario() {
        return Veterinario;
    }

    public void setVeterinario(Boolean veterinario) {
        Veterinario = veterinario;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public String toString() {
        return getNombre()+ " "+ getApellidos();
    }


}
