package com.example.back.lista.infrastructure.db.repositoryImpl;

import com.example.back.lista.infrastructure.db.entity.ListaEnt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ListaEntRepositoryTest {

    @Autowired
    private ListaEntRepository repository;

    @Test
    @Transactional
    void givenListaEnt_whenEliminarPorNombre_thenDelete() {
        ListaEnt listaEnt = new ListaEnt();
        listaEnt.setNombre("Prueba1");
        listaEnt.setDescripcion("Prueba eliminar");
        listaEnt.setListaCancionEnts(new ArrayList<>());
        repository.save(listaEnt);

        repository.eliminarPorNombre("Prueba1");

        List<ListaEnt> list = repository.findAll();

        assertTrue(list.stream().noneMatch(listaEnt1 -> listaEnt1.getNombre().equals("Prueba1")));
    }
}
