package com.example.back.lista.infrastructure.db.repositoryImpl;

import com.example.back.lista.domain.repository.ListaRepo;
import com.example.back.lista.infrastructure.db.entity.ListaEnt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaEntRepository extends ListaRepo<ListaEnt, Integer> {

    @Override
    @Modifying
    @Query(value = "DELETE FROM LISTA WHERE NOMBRE = ?1", nativeQuery = true)
    public void eliminarPorNombre(String nombre);

    @Override
    @Query(value = "SELECT l FROM ListaEnt l WHERE l.nombre = ?1")
    public ListaEnt buscarPorNombre(String nombre);

    @Override
    @Query(value = "SELECT count(*) FROM LISTA WHERE NOMBRE = ?1", nativeQuery = true)
    public Integer existePorNombre(String nombre);
}
