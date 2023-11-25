package com.example.back.lista.infrastructure.db.repositoryImpl;

import com.example.back.lista.domain.repository.ListaCancionRepo;
import com.example.back.lista.infrastructure.db.entity.ListaCancionEnt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaCancionEntRepository extends ListaCancionRepo<ListaCancionEnt, Integer> {

    @Override
    @Modifying
    @Query(value = "DELETE FROM LISTA_CANCION WHERE ID_LISTA = ?1", nativeQuery = true)
    public void eliminarPorIdLista(int id);
}
