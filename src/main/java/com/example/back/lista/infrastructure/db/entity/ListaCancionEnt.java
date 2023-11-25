package com.example.back.lista.infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LISTA_CANCION")
@Getter
@Setter
public class ListaCancionEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idListaCancion;

    @ManyToOne
    @JoinColumn(name = "id_lista")
    private ListaEnt listaEnt;

    @ManyToOne
    @JoinColumn(name = "id_cancion")
    private CancionEnt cancionEnt;
}
