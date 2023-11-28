package com.example.back.auth.infrastructure.rest.controller;

import com.example.back.auth.application.query.ComprobarUsuarioContra;
import com.example.back.auth.infrastructure.db.repositoryImpl.UsuariosLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.example.back.auth.application.query.ComprobarUsuarioContra.ComprobarUsuarioContraBuilder.aComprobarUsuarioContra;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("auth")

public class UsuariosLoginController {

    @Autowired
    private UsuariosLoginRepository repo;

    private static ComprobarUsuarioContra comprobarUsuarioContra;

    @CrossOrigin("http://localhost:4200")
    @GetMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity> consultar(@RequestHeader("Authorization") String auth) {
        comprobarUsuarioContra = aComprobarUsuarioContra()
                .withRepo(repo)
                .withAuth(auth)
                .build();
        return Mono.just(comprobarUsuarioContra.execute());
    }

    @CrossOrigin("http://localhost:4200")
    @PutMapping(value = "/refreshAuth", produces = "application/json")
    public Mono refrescarToken(@RequestHeader("Authorization") String auth) {
        comprobarUsuarioContra = aComprobarUsuarioContra()
                .withRepo(repo)
                .withAuth(auth)
                .build();
        return Mono.just(comprobarUsuarioContra.execute());
    }
}
