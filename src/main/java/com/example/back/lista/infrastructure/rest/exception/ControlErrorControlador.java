package com.example.back.lista.infrastructure.rest.exception;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@CrossOrigin(origins = {"http://localhost:4200"})
public class ControlErrorControlador {

    private JsonObject result;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeException(RuntimeException e) {
        result = new JsonObject().put("error",-100).put("mensaje", "[Exception Response] - Exception: " + e.getMessage()).put("data", new JsonArray());
        return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception e) {
        result = new JsonObject().put("error",-100).put("mensaje", "[Exception Response] - Exception: " + e.getMessage()).put("data", new JsonArray());
        return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
    }
}
