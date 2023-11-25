package com.example.back.auth.infrastructure.db.repositoryImpl;

import com.example.back.auth.domain.repository.UsuariosLoginRepo;
import com.example.back.auth.infrastructure.db.entity.UsuariosLoginEnt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosLoginRepository extends UsuariosLoginRepo<UsuariosLoginEnt, Integer> {

    @Override
    @Query(value = "SELECT ID_USUARIO FROM USUARIOS_LOGIN WHERE USUARIO = ?1 AND CONTRASENA = ?2", nativeQuery = true)
    public Integer comprobarUsuarioContrasena(String usuario, String contrasena);
}
