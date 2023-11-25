package com.example.back.lista.infrastructure.db.repositoryImpl;

import com.example.back.lista.domain.repository.CancionRepo;
import com.example.back.lista.infrastructure.db.entity.CancionEnt;
import org.springframework.stereotype.Repository;

@Repository
public interface CancionEntRepository extends CancionRepo<CancionEnt, Integer> {
}
