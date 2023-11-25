package com.example.back.auth.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosLoginRepo <T, ID> extends JpaRepository<T, ID> {

    public int comprobarUsuarioContrasena(String usuario, String contraena);
}
