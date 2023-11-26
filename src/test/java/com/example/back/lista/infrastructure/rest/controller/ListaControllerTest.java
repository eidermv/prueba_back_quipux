package com.example.back.lista.infrastructure.rest.controller;

import com.example.back.auth.application.query.ValidacionAuth;
import com.example.back.lista.application.query.ConsultarGeneroSpotify;
import com.example.back.lista.infrastructure.db.repositoryImpl.CancionEntRepository;
import com.example.back.lista.infrastructure.db.repositoryImpl.ListaCancionEntRepository;
import com.example.back.lista.infrastructure.db.repositoryImpl.ListaEntRepository;
import com.example.back.lista.infrastructure.rest.mapper.ListaMapImpl;
import com.example.back.lista.infrastructure.rest.mapper.ListaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ListaController.class)
@ComponentScan(basePackages = {"com.example.back.infrastructure"})
public class ListaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidacionAuth validacionAuth;

    @MockBean
    private ListaEntRepository listaEntRepository;

    @MockBean
    private ListaCancionEntRepository listaCancionEntRepository;

    @MockBean
    private CancionEntRepository cancionEntRepository;

    @MockBean
    private ConsultarGeneroSpotify consultarGeneroSpotify;

    @MockBean
    private ListaMapper listaMapper;
    @MockBean
    private ListaMapImpl listaMap;

    @Test
    public void givenNameList_whenListByName_thenReturnList() throws Exception {
        given(validacionAuth.execute()).willReturn(ResponseEntity.status(200).body(anyString()));
        mockMvc.perform(get("/lists/Prueba")
                .header("Authorization", "Bearer eyFMyNTYifQ"))
                .andExpect(status().isOk());
    }
}
