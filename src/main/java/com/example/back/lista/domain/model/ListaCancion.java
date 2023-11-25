package com.example.back.lista.domain.model;

public class ListaCancion {

    private int idListaCancion;
    private Lista lista;
    private Cancion cancion;

    public int getIdListaCancion() {
        return idListaCancion;
    }

    public void setIdListaCancion(int idListaCancion) {
        this.idListaCancion = idListaCancion;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }
}
