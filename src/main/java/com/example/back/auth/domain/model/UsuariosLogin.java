package com.example.back.auth.domain.model;

import com.example.back.lista.domain.model.ListaCancion;

import java.util.Set;

public class UsuariosLogin {

    private int idUsuario;
    private String usuario;
    private String contrasena;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
