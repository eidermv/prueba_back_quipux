package com.example.back.lista.infrastructure.db.entity;

import com.example.back.lista.domain.model.Cancion;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "LISTA")
public class ListaEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private int idLista;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "listaEnt", cascade = CascadeType.REMOVE)
    @JsonProperty("canciones")
    private List<ListaCancionEnt> listaCancionEnts;
}
