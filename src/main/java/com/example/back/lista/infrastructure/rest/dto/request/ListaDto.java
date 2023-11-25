package com.example.back.lista.infrastructure.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListaDto {

    private int idLista;
    @NotNull
    private String nombre;
    @NotNull
    private String descripcion;
    @JsonProperty("canciones")
    private List<CancionDto> listaCancions;
}
