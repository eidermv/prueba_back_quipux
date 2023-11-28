package com.example.back.lista.infrastructure.rest.controller;

import com.example.back.auth.application.query.ValidacionAuth;
import com.example.back.lista.application.command.*;
import com.example.back.lista.application.query.BuscarPorNombreLista;
import com.example.back.lista.application.query.ConsultarGeneroSpotify;
import com.example.back.lista.application.query.ListarLista;
import com.example.back.lista.infrastructure.db.entity.CancionEnt;
import com.example.back.lista.infrastructure.db.entity.ListaCancionEnt;
import com.example.back.lista.infrastructure.db.entity.ListaEnt;
import com.example.back.lista.infrastructure.db.repositoryImpl.CancionEntRepository;
import com.example.back.lista.infrastructure.db.repositoryImpl.ListaCancionEntRepository;
import com.example.back.lista.infrastructure.db.repositoryImpl.ListaEntRepository;
import com.example.back.lista.infrastructure.rest.dto.request.ListaDto;
import com.example.back.lista.infrastructure.rest.mapper.ListaMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static com.example.back.lista.application.command.EliminarPorIdLista.EliminarPorIdListaBuilder.anEliminarPorIdLista;
import static com.example.back.lista.application.command.GuardarCancion.GuardarCancionBuilder.aGuardarCancion;
import static com.example.back.lista.application.command.GuardarLista.GuardarListaBuilder.aGuardarLista;
import static com.example.back.lista.application.command.GuardarListaCancion.GuardarListaCancionBuilder.aGuardarListaCancion;
import static com.example.back.lista.application.query.BuscarPorNombreLista.BuscarPorNombreListaBuilder.aBuscarPorNombreLista;
import static com.example.back.lista.application.query.ListarLista.ListarListaBuilder.aListarLista;


@RestController
@RequestMapping("lists")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ListaController {

    @Autowired
    private ListaEntRepository listaEntRepository;
    @Autowired
    private ListaCancionEntRepository listaCancionEntRepository;
    @Autowired
    private CancionEntRepository cancionEntRepository;

    private static ListarLista<ListaEnt> listas;
    private static BuscarPorNombreLista<ListaEnt> buscarPorNombreLista;
    private static GuardarLista<ListaEnt> guardarLista;
    private static GuardarListaCancion<ListaCancionEnt> guardarListaCancion;
    private static GuardarCancion<CancionEnt> guardarCancion;
    private static EliminarPorIdLista eliminarPorIdLista;
    @Autowired
    private ConsultarGeneroSpotify consultarGeneroSpotify;

    @Qualifier("ListaMapImpl")
    @Autowired
    private ListaMapper listaMapper;

    @Autowired
    private ValidacionAuth validacionAuth;

    @GetMapping(value = "/generos", produces = "application/json")
    public Mono<ResponseEntity> listarGeneros(@RequestHeader("Authorization") String auth) {
        validacionAuth.setAuth(auth);
        ResponseEntity responseEntity = validacionAuth.execute();
        if (responseEntity.getStatusCode().value() == 200) {
            return consultarGeneroSpotify.execute();
        } else
            return Mono.just(responseEntity);
    }

    @GetMapping(value = "", produces = "application/json")
    public Mono<ResponseEntity> listarTodos(@RequestHeader("Authorization") String auth) {
        validacionAuth.setAuth(auth);
        ResponseEntity responseEntity = validacionAuth.execute();
        if (responseEntity.getStatusCode().value() == 200) {
            listas = aListarLista()
                    .withRepo(listaEntRepository)
                    .build();
            List<ListaDto> dtoList = listas.execute().stream().map(listaEnt -> listaMapper.toDto(listaEnt)).toList();
            return Mono.just(ResponseEntity.status(200).body(dtoList));
        } else
            return Mono.just(responseEntity);
    }

    @GetMapping(value = "/{listName}", produces = "application/json")
    public Mono<ResponseEntity> buscarPorNombre(@RequestHeader("Authorization") String auth, @PathVariable String listName) {
        validacionAuth.setAuth(auth);
        ResponseEntity responseEntity = validacionAuth.execute();
        if (responseEntity.getStatusCode().value() == 200) {
            buscarPorNombreLista = aBuscarPorNombreLista()
                    .withData(listName)
                    .withRepo(listaEntRepository)
                    .build();
            ListaEnt lista = buscarPorNombreLista.execute();
            if (Objects.isNull(lista)) {
                return Mono.just(ResponseEntity.status(404).body(null));
            } else {
                return Mono.just(ResponseEntity.status(200).body(listaMapper.toDto(lista)));
            }
        } else
            return Mono.just(responseEntity);
    }

    @PostMapping(value = "", produces = "application/json")
    public Mono<ResponseEntity> guardarLista(@RequestHeader("Authorization") String auth, @Valid @RequestBody ListaDto listaDto) {
        validacionAuth.setAuth(auth);
        ResponseEntity responseEntity = validacionAuth.execute();
        if (responseEntity.getStatusCode().value() == 200) {
            ListaEnt listaEnt = listaMapper.toEntity(listaDto);
            guardarLista = aGuardarLista()
                    .withData(listaEnt)
                    .withRepo(listaEntRepository)
                    .build();
            ListaEnt lista = guardarLista.execute();
            if (Objects.isNull(lista)) {
                return Mono.just(ResponseEntity.status(304).body(listaMapper.toDto(lista)));
            } else {
                listaEnt.getListaCancionEnts().forEach(listaCancionEnt -> {
                    guardarListaCancion = aGuardarListaCancion()
                            .withData(listaCancionEnt)
                            .withRepo(listaCancionEntRepository)
                            .build();
                    guardarCancion = aGuardarCancion()
                            .withData(listaCancionEnt.getCancionEnt())
                            .withRepo(cancionEntRepository)
                            .build();
                    CancionEnt cancionEnt = guardarCancion.execute();
                    ListaCancionEnt listaCancionEnt1 = guardarListaCancion.execute();
                });
                return Mono.just(ResponseEntity.status(201).body(listaMapper.toDto(lista)));
            }
        } else
            return Mono.just(responseEntity);
    }

    @DeleteMapping(value = "/{listName}", produces = "application/json")
    public Mono<ResponseEntity> eliminarPorNombre(@RequestHeader("Authorization") String auth, @PathVariable String listName) {
        validacionAuth.setAuth(auth);
        ResponseEntity responseEntity = validacionAuth.execute();
        if (responseEntity.getStatusCode().value() == 200) {
            buscarPorNombreLista = aBuscarPorNombreLista()
                    .withData(listName)
                    .withRepo(listaEntRepository)
                    .build();
            ListaEnt listaEnt = buscarPorNombreLista.execute();
            if (!Objects.isNull(listaEnt)) {

                eliminarPorIdLista = anEliminarPorIdLista()
                        .withData(listaEnt.getIdLista())
                        .withRepo(listaEntRepository)
                        .build();
                if (Boolean.TRUE.equals(eliminarPorIdLista.execute())) {
                    return Mono.just(ResponseEntity.status(204).body(true));
                } else {
                    return Mono.just(ResponseEntity.status(404).body(false));
                }
            } else {
                return Mono.just(ResponseEntity.status(404).body(false));
            }
        } else
            return Mono.just(responseEntity);
    }
}
