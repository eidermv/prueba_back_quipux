package com.example.back.lista.infrastructure.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "CANCION")
public class CancionEnt {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCancion;
    private String titulo;
    private String artista;
    private String album;
    private String genero;
    private int anno;
    @OneToMany(mappedBy = "cancionEnt")
    @JsonProperty("listas")
    private List<ListaCancionEnt> listaCancionEnts;

}
