package com.example.back.auth.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UsuariosLoginRepo <T, ID> extends JpaRepository<T, ID> {

    public int comprobarUsuarioContrasena(String usuario, String contrasena);
}
