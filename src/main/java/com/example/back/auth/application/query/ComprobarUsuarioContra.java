package com.example.back.auth.application.query;

import com.example.back.auth.domain.model.Respuesta;
import com.example.back.auth.domain.model.UsuariosLogin;
import com.example.back.auth.domain.repository.UsuariosLoginRepo;
import com.example.back.shared.application.ValidacionJWT;
import com.example.back.shared.application.query.Query;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class ComprobarUsuarioContra<T> implements Query<ResponseEntity> {
    private String auth;
    private UsuariosLoginRepo repo;

    Base64.Decoder b64dec = Base64.getDecoder();
    private final String JWT_ISSUER;
    private final String JWT_ID_HEADER;
    @Autowired
    private ValidacionJWT jwt;

    private ComprobarUsuarioContra(
            @Value("${JWT_ID_HEADER}") String JWT_ID_HEADER,
            @Value("${JWT_ISSUER}") String JWT_ISSUER
    ) {
        this.JWT_ISSUER=JWT_ISSUER;
        this.JWT_ID_HEADER=JWT_ID_HEADER;
    }

    @Override
    public ResponseEntity execute() {
        String[] authStr = auth.split(" ");
        Respuesta respuesta;
        try {
            switch (authStr[0]) {
                case "Basic" -> {
                    if (authStr.length > 1) {
                        String username = null;
                        String password = null;
                        authStr[1] = new String(b64dec.decode(authStr[1].getBytes("UTF-8")), "UTF-8");
                        username = authStr[1].split(":")[0].trim();
                        password = authStr[1].split(":")[1].trim();

                        System.out.println("user " + username + " pass " + password);

                        String jwtIdCode = "sys_quipux:" + Long.toString(System.currentTimeMillis());

                        MessageDigest digest = MessageDigest.getInstance("SHA-1");
                        digest.reset();
                        digest.update(jwtIdCode.getBytes("utf8"));

                        jwtIdCode = JWT_ID_HEADER + String.format("%040X", new BigInteger(1, digest.digest()));


                        String finalJwtIdCode = jwtIdCode;

                        int idUsuario = repo.comprobarUsuarioContrasena(username, password);
                            if (idUsuario == 0) {
                                respuesta = new Respuesta(401, "Usuario o contrasena incorrecto");
                                return ResponseEntity.status(401).body(respuesta.toString());
                            } else {
                                int lifespan = 129600000;
                                UsuariosLogin usuariosLogin = new UsuariosLogin();
                                usuariosLogin.setIdUsuario(idUsuario);
                                usuariosLogin.setUsuario(username);
                                usuariosLogin.setContrasena(password);
                                String payload = usuariosLogin.toString();
                                JsonObject aux = new JsonObject();
                                String jwtStr = jwt.createJWT("Quipux-Web", finalJwtIdCode, JWT_ISSUER, payload,
                                        "Prueba", lifespan);
                                aux.put("mensaje", "Token privado generado");
                                aux.put("key", jwtStr);
                                aux.put("error", 0);

                                return ResponseEntity.status(401).body(aux.toString());
                            }


                    } else {
                        try {
                            throw new Exception("Sin datos de autenticación");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        respuesta = new Respuesta(401, "Sin datos de autenticación");
                        return ResponseEntity.status(401).body(respuesta.toString());
                    }
                }
                default -> {
                    respuesta = new Respuesta(401, "Contexto de seguridad no autorizado");
                    return ResponseEntity.status(401).body(respuesta.toString());

                }
            }

        } catch (Exception e) {
            respuesta = new Respuesta(401, "Servicio no autorizado, inexistente o petición inválida");
            return ResponseEntity.status(401).body(respuesta.toString());


        }
    }
}
