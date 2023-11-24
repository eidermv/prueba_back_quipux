package com.example.back.lista.infrastructure.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CancionEnt {
    @Id
    private int idCancion;
    private String titulo;
    private String artista;
    private String album;
    private String genero;
    private int anno;


}
