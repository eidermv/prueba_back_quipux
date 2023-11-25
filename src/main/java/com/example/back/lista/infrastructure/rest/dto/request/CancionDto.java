package com.example.back.lista.infrastructure.rest.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CancionDto {
    private int idCancion;
    @NotNull
    private String titulo;
    private String artista;
    private String album;
    @NotNull
    private String genero;
    @NotNull
    private int anno;



}
