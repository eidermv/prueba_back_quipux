package com.example.back.lista.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CancionRepo<T, ID> extends JpaRepository<T, ID> {

}
