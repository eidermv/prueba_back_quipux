package com.example.back.shared.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ValidacionJWT {
    Base64.Decoder b64dec = Base64.getDecoder();
    private static final Logger LOG = Logger.getLogger(ValidacionJWT.class.getSimpleName());
    private String SECRET_KEY;


    public ValidacionJWT(
            @Value("${property.SECRET_KEY}") String SECRET_KEY
    ) {
        try {

            this.SECRET_KEY = SECRET_KEY;

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "[ValidationJWT]: Algo salió mal con las propiedades. " + ex.toString());
        }
    }

    // Sample method to construct a JWT
    public String createJWT(String origin, String id, String issuer, String subject, String audience, long ttlMillis) {
        try {
            // The JWT signature algorithm we will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            // We will sign our JWT with our ApiKey secret
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            // Let's set the JWT Claims
            JwtBuilder builder = Jwts.builder().setHeaderParam("origin", origin).setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer).setAudience(audience).signWith(signatureAlgorithm, signingKey);

            // if it has been specified, let's add the expiration
            if (ttlMillis >= 0) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }

            // Builds the JWT and serializes it to a compact, URL-safe string
            return builder.compact();
        } catch (Exception e) {
            Logger.getLogger(ValidacionJWT.class.getName()).log(Level.SEVERE, "Error al validar servicio", e);
            return null;
        }
    }

    public Claims decodeJWT(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).build()
                .parseClaimsJws(jwt).getBody();
                //Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).build().parseClaimsJws(jwt).getBody();
        return claims;
    }

}
