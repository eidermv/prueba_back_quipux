package com.example.back.shared.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ValidacionRSA {
    private KeyPairGenerator keygen;
    private static KeyPair keypair;
    private static Cipher rsaCipher;
    static Base64.Encoder encoder = Base64.getEncoder();
    static Base64.Decoder decoder = Base64.getDecoder();
    private PrivateKey clave;


    public ValidacionRSA() {
        try {
            this.keygen = KeyPairGenerator.getInstance("RSA");
            this.keypair = keygen.generateKeyPair();
            this.rsaCipher = Cipher.getInstance("RSA");
        } catch (Exception ex) {
            System.out.println("[ValidationJWT]: Algo salió mal con las propiedades. " + ex.toString());
        }
    }

    public String getClave(){
        return encoder.encodeToString(this.clave.getEncoded());
    }
    public String cifrar(String data) {
        try {
            rsaCipher.init(Cipher.ENCRYPT_MODE, keypair.getPublic());
            byte[] mensajeCifrado = rsaCipher.doFinal(data.getBytes("UTF8"));
            this.clave = keypair.getPrivate();
            return encoder.encodeToString(mensajeCifrado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean comprobar(String data, String cifrado, String clave) {
        try {
            byte[] privada_d = decoder.decode(clave);
            byte[] data_d = decoder.decode(cifrado);
            PrivateKey key = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(privada_d));

            rsaCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] mensajeDescifrado = rsaCipher.doFinal(data_d);
            String mensajeDescifrado2 = new String(mensajeDescifrado, "UTF8");
            return data.equals(mensajeDescifrado2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
