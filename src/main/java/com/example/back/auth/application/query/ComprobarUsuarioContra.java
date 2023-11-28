package com.example.back.auth.application.query;

import com.example.back.auth.domain.model.Respuesta;
import com.example.back.auth.domain.model.UsuariosLogin;
import com.example.back.auth.domain.repository.UsuariosLoginRepo;
import com.example.back.shared.application.ValidacionJWT;
import com.example.back.shared.application.ValidacionRSA;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;

@Component
public class ComprobarUsuarioContra extends QueryAuth<ResponseEntity> {

    private ComprobarUsuarioContra() {
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

                        Integer idUsuario = repo.comprobarUsuarioContrasena(username, password);
                        if (Objects.isNull(idUsuario)) {
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
                            String temp = rsa.cifrar(String.valueOf(idUsuario));
                            String nuevaClave = rsa.getClave();
                            usuariosLogin.setContrasena(nuevaClave);
                            aux.put("mensaje", "Token privado generado");
                            aux.put("key", jwtStr);
                            aux.put("data", usuariosLogin);
                            aux.put("error", 0);
                            repo.guardarTemp(String.valueOf(idUsuario), temp);

                            return ResponseEntity.status(200).body(aux.toString());
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
                case "Refresh" -> {
                    if (authStr.length > 1) {
                        authStr[1] = new String(b64dec.decode(authStr[1].getBytes("UTF-8")), "UTF-8");

                        JsonObject usuario = new JsonObject(authStr[1]);

                        int idUsuario = usuario.getInteger("idUsuario");
                        String temp = repo.obtenerTemp(idUsuario);

                        String privada = usuario.getString("contrasena");
                        String usuarioR = usuario.getString("usuario");

                        if (rsa.comprobar(String.valueOf(idUsuario), temp, privada)) {
                            String jwtIdCode = "sys_quipux:" + Long.toString(System.currentTimeMillis());

                            MessageDigest digest = MessageDigest.getInstance("SHA-1");
                            digest.reset();
                            digest.update(jwtIdCode.getBytes("utf8"));

                            jwtIdCode = JWT_ID_HEADER + String.format("%040X", new BigInteger(1, digest.digest()));


                            String finalJwtIdCode = jwtIdCode;
                            int lifespan = 129600000;
                            UsuariosLogin usuariosLogin = new UsuariosLogin();
                            usuariosLogin.setIdUsuario(idUsuario);
                            usuariosLogin.setUsuario(usuarioR);
                            usuariosLogin.setContrasena(privada);
                            String payload = usuariosLogin.toString();
                            JsonObject aux = new JsonObject();
                            String jwtStr = jwt.createJWT("Quipux-Web", finalJwtIdCode, JWT_ISSUER, payload,
                                    "Prueba", lifespan);

                            aux.put("mensaje", "Token actualizado");
                            aux.put("key", jwtStr);
                            aux.put("data", usuariosLogin);
                            aux.put("error", 0);
                            return ResponseEntity.status(200).body(aux.toString());
                        }
                        respuesta = new Respuesta(401, "Contexto de seguridad no autorizado");
                        return ResponseEntity.status(401).body(respuesta.toString());

                    }
                    respuesta = new Respuesta(401, "Contexto de seguridad no autorizado");
                    return ResponseEntity.status(401).body(respuesta.toString());
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

    @Component
    public static final class ComprobarUsuarioContraBuilder {
        private String auth;
        private UsuariosLoginRepo repo;

        private static String JWT_ISSUER;
        private static String JWT_ID_HEADER;
        private static ValidacionJWT jwt;
        private static ValidacionRSA rsa;

        public ComprobarUsuarioContraBuilder() {

        }

        @Autowired
        private void setJWT_ID_HEADER(@Value("${property.JWT_ID_HEADER}") String JWT_ID_HEADER) {
            this.JWT_ID_HEADER = JWT_ID_HEADER;
        }

        @Autowired
        private void setJWT_ISSUER(@Value("${property.JWT_ISSUER}") String JWT_ISSUER) {
            this.JWT_ISSUER = JWT_ISSUER;
        }

        @Autowired
        private void setJwt(ValidacionJWT jwt) {
            this.jwt = jwt;
        }

        @Autowired
        private void setRsa(ValidacionRSA rsa) {
            this.rsa = rsa;
        }

        public static ComprobarUsuarioContraBuilder aComprobarUsuarioContra() {
            return new ComprobarUsuarioContraBuilder();
        }

        public ComprobarUsuarioContraBuilder withAuth(String auth) {
            this.auth = auth;
            return this;
        }

        public ComprobarUsuarioContraBuilder withRepo(UsuariosLoginRepo repo) {
            this.repo = repo;
            return this;
        }

        public ComprobarUsuarioContra build() {
            ComprobarUsuarioContra comprobarUsuarioContra = new ComprobarUsuarioContra();
            comprobarUsuarioContra.JWT_ID_HEADER = this.JWT_ID_HEADER;
            comprobarUsuarioContra.JWT_ISSUER = this.JWT_ISSUER;
            comprobarUsuarioContra.auth = this.auth;
            comprobarUsuarioContra.repo = this.repo;
            comprobarUsuarioContra.jwt = this.jwt;
            comprobarUsuarioContra.rsa = this.rsa;
            return comprobarUsuarioContra;
        }
    }
}
