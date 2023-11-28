package com.example.back.auth.infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "USUARIOS_LOGIN")
public class UsuariosLoginEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    private String usuario;
    private String contrasena;
    @Column(length = 3000)
    private String temp;

}
