package com.example.back.auth.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosLoginRepo <T, ID> extends JpaRepository<T, ID> {

    public T comprobarUsuarioContrasena(String usuario, String contraena);
}
