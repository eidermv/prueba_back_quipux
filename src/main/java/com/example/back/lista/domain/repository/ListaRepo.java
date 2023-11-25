package com.example.back.lista.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ListaRepo<T, ID> extends JpaRepository<T, ID> {

    public T buscarPorNombre(String nombre);
    public void eliminarPorNombre(String nombre);
    public Integer existePorNombre(String nombre);
}
