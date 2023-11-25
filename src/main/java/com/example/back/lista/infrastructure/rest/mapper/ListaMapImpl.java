package com.example.back.lista.infrastructure.rest.mapper;

import com.example.back.lista.infrastructure.db.entity.CancionEnt;
import com.example.back.lista.infrastructure.db.entity.ListaCancionEnt;
import com.example.back.lista.infrastructure.db.entity.ListaEnt;
import com.example.back.lista.infrastructure.rest.dto.request.CancionDto;
import com.example.back.lista.infrastructure.rest.dto.request.ListaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("ListaMapImpl")
public class ListaMapImpl implements ListaMapper {

    @Autowired
    private CancionEntMapper cancionEntMapper;

    @Override
    public ListaEnt toEntity(ListaDto source) {
        ListaEnt listaEnt = new ListaEnt();
        listaEnt.setIdLista(source.getIdLista());
        listaEnt.setNombre(source.getNombre());
        listaEnt.setDescripcion(source.getDescripcion());
        List<ListaCancionEnt> listaCancionEnts = source.getListaCancions().stream().map(cancionDto -> {
            ListaCancionEnt listaCancionEnt = new ListaCancionEnt();
            CancionEnt cancionEnt = cancionEntMapper.toEntity(cancionDto);
            listaCancionEnt.setCancionEnt(cancionEnt);
            listaCancionEnt.setListaEnt(listaEnt);
            return listaCancionEnt;
        }).toList();
        listaEnt.setListaCancionEnts(listaCancionEnts);
        return listaEnt;
    }

    @Override
    public ListaDto toDto(ListaEnt target) {
        ListaDto listaDto = new ListaDto();
        listaDto.setIdLista(target.getIdLista());
        listaDto.setDescripcion(target.getDescripcion());
        listaDto.setNombre(target.getNombre());
        //private List<CancionDto> listaCancions;
        List<CancionDto> listaCancions = target.getListaCancionEnts().stream()
                .map(listaCancionEnt -> cancionEntMapper.toDto(listaCancionEnt.getCancionEnt()))
                .toList();
        listaDto.setListaCancions(listaCancions);
        return listaDto;
    }
}
