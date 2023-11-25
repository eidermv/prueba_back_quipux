package com.example.back.auth.application.query;

import com.example.back.auth.domain.model.Respuesta;
import com.example.back.auth.infrastructure.db.entity.UsuariosLoginEnt;
import com.example.back.shared.application.query.Query;
import io.jsonwebtoken.Claims;
import io.vertx.core.json.JsonObject;
import org.springframework.http.ResponseEntity;

public class ValidacionAuth extends QueryAuth<ResponseEntity> {

    private ValidacionAuth() {}
    @Override
    public ResponseEntity execute() {
        JsonObject responseJson = new JsonObject();
        Claims jwtClaims = null;
        Respuesta respuesta;
        try {
            String[] authStr = auth.split(" ");
            switch (authStr[0]) {
                case "Bearer" -> {
                    // Validación de Token
                    try {
                        jwtClaims = jwt.decodeJWT(authStr[1]);
                        if (jwtClaims.getIssuer().equals(JWT_ISSUER)) {
                            respuesta = new Respuesta(0, "Correcto");
                            return ResponseEntity.status(200).body(respuesta.toString());

                        }else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        respuesta = new Respuesta(-3, "Usuario no autorizado");
                        return ResponseEntity.status(401).body(respuesta.toString());

                    }
                }
                default -> {
                    respuesta = new Respuesta(-2, "Contexto de seguridad no autorizado");
                    return ResponseEntity.status(401).body(respuesta.toString());

                }
            }

        }
        catch(Exception e){
            respuesta = new Respuesta(-1, "Servicio no autorizado, inexistente o petición inválida");
            return ResponseEntity.status(401).body(respuesta.toString());

        }
    }
}
