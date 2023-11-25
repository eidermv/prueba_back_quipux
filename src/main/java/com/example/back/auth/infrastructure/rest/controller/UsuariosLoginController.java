package com.example.back.auth.infrastructure.rest.controller;

import com.example.back.auth.application.query.ComprobarUsuarioContra;
import com.example.back.auth.infrastructure.db.repositoryImpl.UsuariosLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.example.back.auth.application.query.ComprobarUsuarioContra.ComprobarUsuarioContraBuilder.aComprobarUsuarioContra;

@RestController
@RequestMapping("auth")
public class UsuariosLoginController {

    @Autowired
    private UsuariosLoginRepository repo;

    private static ComprobarUsuarioContra comprobarUsuarioContra;

    @GetMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity> consultar(@RequestHeader("Authorization") String auth) {
        comprobarUsuarioContra = aComprobarUsuarioContra()
                .withRepo(repo)
                .withAuth(auth)
                .build();
        return Mono.just(comprobarUsuarioContra.execute());
    }
}
