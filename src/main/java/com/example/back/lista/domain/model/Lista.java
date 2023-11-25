package com.example.back.lista.domain.model;

import java.util.Set;

public class Lista {
    private int idLista;
    private String nombre;
    private String descripcion;
    private Set<ListaCancion> listaCancions;

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<ListaCancion> getListaCancions() {
        return listaCancions;
    }

    public void setListaCancions(Set<ListaCancion> listaCancions) {
        this.listaCancions = listaCancions;
    }
}
