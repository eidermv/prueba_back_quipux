package com.example.back.auth.application.query;

import com.example.back.auth.domain.repository.UsuariosLoginRepo;
import com.example.back.shared.application.ValidacionJWT;
import com.example.back.shared.application.query.Query;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public abstract class QueryAuth<T> implements Query<T> {

    String auth;
    UsuariosLoginRepo repo;
    Base64.Decoder b64dec = Base64.getDecoder();

    String JWT_ISSUER;
    String JWT_ID_HEADER;
    ValidacionJWT jwt;

}
