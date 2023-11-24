package com.example.back.lista.infrastructure.db.entity;

import com.example.back.lista.domain.model.Cancion;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class ListaEnt {
    @Id
    private int idLista;
    private String nombre;
    private String descripcion;
    private List<Cancion> canciones;
}
