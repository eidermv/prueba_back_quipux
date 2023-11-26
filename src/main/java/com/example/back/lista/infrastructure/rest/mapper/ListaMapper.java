package com.example.back.lista.infrastructure.rest.mapper;

import com.example.back.lista.infrastructure.db.entity.ListaEnt;
import com.example.back.lista.infrastructure.rest.dto.request.ListaDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ListaMapper {

    public ListaEnt toEntity(ListaDto source);
    public ListaDto toDto(ListaEnt target);
}
