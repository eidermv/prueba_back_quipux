package com.example.back.lista.domain.model;

import java.util.Set;

public class Cancion {
    private int idCancion;
    private String titulo;
    private String artista;
    private String album;
    private String genero;
    private int anno;
    private Set<ListaCancion> listaCancions;

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public Set<ListaCancion> getListaCancions() {
        return listaCancions;
    }

    public void setListaCancions(Set<ListaCancion> listaCancions) {
        this.listaCancions = listaCancions;
    }


}
