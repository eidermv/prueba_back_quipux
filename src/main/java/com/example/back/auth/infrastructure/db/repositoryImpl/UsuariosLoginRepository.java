package com.example.back.auth.infrastructure.db.repositoryImpl;

import com.example.back.auth.domain.repository.UsuariosLoginRepo;
import com.example.back.auth.infrastructure.db.entity.UsuariosLoginEnt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuariosLoginRepository extends UsuariosLoginRepo<UsuariosLoginEnt, Integer> {

    @Override
    @Query(value = "SELECT ID_USUARIO FROM USUARIOS_LOGIN WHERE USUARIO = ?1 AND CONTRASENA = ?2", nativeQuery = true)
    public Integer comprobarUsuarioContrasena(String usuario, String contrasena);

    @Override
    @Query(value = "SELECT TEMP FROM USUARIOS_LOGIN WHERE ID_USUARIO = :idUsuario", nativeQuery = true)
    String obtenerTemp(@Param("idUsuario") int idUsuario);

    @Override
    @Transactional
    @Modifying
    @Query(value = "UPDATE USUARIOS_LOGIN SET TEMP = :temp WHERE ID_USUARIO = :idUsuario", nativeQuery = true)
    int guardarTemp(@Param("idUsuario") String idUsuario, @Param("temp") String temp);
}
