package com.example.back.auth.domain.model;

import io.vertx.core.json.JsonObject;

public class UsuariosLogin{

    private int idUsuario;
    private String usuario;
    private String contrasena;
    private String temp;
    private JsonObject jsonObject;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

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

    @Override
    public String toString() {
        this.jsonObject = new JsonObject()
                .put("idUsuario", this.idUsuario)
                .put("usuario", this.usuario)
                .put("contrasena", this.contrasena);
        return this.jsonObject.toString();
    }
}
