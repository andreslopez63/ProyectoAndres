package com.example.proyectoandres.ui.notifications;

public class Notification {

    int Anioc, Mesc, Diac, Horac;
    String Comentario, UsuarioAsignado;

    public Notification(){};
    public Notification(int anioc, int mesc, int diac, int horac, String comentario, String usuarioAsignado) {
        Anioc = anioc;
        Mesc = mesc;
        Diac = diac;
        Horac = horac;
        Comentario = comentario;
        UsuarioAsignado = usuarioAsignado;
    }

    public int getAnioc() {
        return Anioc;
    }

    public void setAnioc(int anioc) {
        Anioc = anioc;
    }

    public int getMesc() {
        return Mesc;
    }

    public void setMesc(int mesc) {
        Mesc = mesc;
    }

    public int getDiac() {
        return Diac;
    }

    public void setDiac(int diac) {
        Diac = diac;
    }

    public int getHorac() {
        return Horac;
    }

    public void setHorac(int horac) {
        Horac = horac;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public String getUsuarioAsignado() {
        return UsuarioAsignado;
    }

    public void setUsuarioAsignado(String usuarioAsignado) {
        UsuarioAsignado = usuarioAsignado;
    }
}
