package com.example.back.auth.domain.model;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Respuesta {

    private int error;
    private String mensaje;
    private JsonArray data;

    private JsonObject jsonObject;


    public Respuesta(int error, String mensaje, String data) {
        this.error = error;
        this.mensaje = mensaje;
        this.data = new JsonArray(data);
        this.jsonObject = new JsonObject().put("error", error).put("mensaje", mensaje).put("data", this.data);
    }

    public Respuesta(int error, String mensaje, JsonArray data) {
        this.error = error;
        this.mensaje = mensaje;
        this.data = data;
        this.jsonObject = new JsonObject().put("error", error).put("mensaje", mensaje).put("data", data);
    }

    public Respuesta(int error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
        this.data = new JsonArray();
        this.jsonObject = new JsonObject().put("error", error).put("mensaje", mensaje).put("data", data);
    }

    public Respuesta(String valor) {
        valor = valor.replace("{", "");
        if(valor.contains("error") && valor.contains("mensaje") && valor.contains("data")){
            String[] datos = valor.split(",");
            for (String dato :
                    datos) {
                if (dato.contains("error")){
                    setError(Integer.parseInt(dato.split(":")[1]));
                }
                if (dato.contains("mensaje")){
                    setMensaje(dato.split(":")[1]);
                }
                if (dato.contains("data")){
                    setData(dato.split(":")[1]);
                }
                this.jsonObject = new JsonObject().put("error", this.error).put("mensaje", this.mensaje).put("data", this.data);
            }
        } else {
            new Respuesta(-1001, "No se ha construido Respuesta faltan campos", new JsonArray());
        }

    }

    public String getData() {
        return data.toString();
    }

    public void setData(String data) {
        this.data = new JsonArray(data);
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return this.jsonObject.encode();
    }
}
