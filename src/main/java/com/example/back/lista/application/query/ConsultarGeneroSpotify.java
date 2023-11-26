package com.example.back.lista.application.query;

import com.example.back.shared.application.query.Query;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class ConsultarGeneroSpotify implements Query<Mono> {

    WebClient client;
    private String urlGeneros = "https://api.spotify.com/v1/recommendations/available-genre-seeds";
    private final String accessBasic;
    private final String clientId;
    private final String clientSecret;

    private String urlAccess = "https://accounts.spotify.com/api/token";
    private String token = "";

    private ConsultarGeneroSpotify(
            @Value("${webclient.access_basic}") String accessBasic,
            @Value("${webclient.client_id}") String clientId,
            @Value("${webclient.client_secret}") String clientSecret) {
        this.accessBasic = accessBasic;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        WebClientOptions options = new WebClientOptions()
                .setUserAgent("My-App/1.2.3");
        options.setKeepAlive(false);
        this.client = WebClient.create(Vertx.vertx(), options);
    }

    @Override
    public Mono execute() {

        return Mono.create(subscriber -> {
            this.obtenerToken()
                    .onSuccess(bufferHttpResponse -> {
                        //System.out.println("Entra aqui una vez -----");
                        if (bufferHttpResponse.statusCode() == 200) {
                            this.token = bufferHttpResponse.bodyAsJsonObject().getString("access_token");
                            this.client
                                    .getAbs(this.urlGeneros)
                                    .authentication(new TokenCredentials(this.token))
                                    .send()
                                    .onSuccess(bufferHttpResponse1 -> {
                                        if (bufferHttpResponse1.statusCode()==200){
                                            JsonObject rta = new JsonObject()
                                                    .put("generos", bufferHttpResponse1.bodyAsJsonObject().getJsonArray("genres"));
                                            subscriber.success(ResponseEntity.status(200).body(rta.toString()));
                                            /*subscriber.onNext(ResponseEntity.status(200).body(rta.toString()));
                                            subscriber.onComplete();*/
                                        } else {
                                            JsonObject rta = new JsonObject().put("error", bufferHttpResponse1.statusMessage());
                                            subscriber.success(ResponseEntity.status(bufferHttpResponse1.statusCode()).body(rta.toString()));
                                        }
                                        /*subscriber.onNext(ResponseEntity.status(bufferHttpResponse1.statusCode()).body(bufferHttpResponse1.statusMessage()));
                                        subscriber.onComplete();*/
                                    })
                                    .onFailure(throwable -> {
                                        System.out.println("ERROR -> " + throwable.getMessage());
                                        JsonObject rta = new JsonObject().put("error", throwable.getMessage());
                                        subscriber.success(ResponseEntity.status(421).body(rta.toString()));

                                        /*subscriber.onNext(ResponseEntity.status(421).body(throwable.getMessage()));
                                        subscriber.onComplete();*/
                                    });
                        }
                    })
                    .onFailure(throwable -> {
                        System.out.println("ERROR -> " + throwable.getMessage());
                        JsonObject rta = new JsonObject().put("error", throwable.getMessage());
                        subscriber.success(ResponseEntity.status(421).body(rta.toString()));

                        /*subscriber.onNext(ResponseEntity.status(421).body(throwable.getMessage()));
                        subscriber.onComplete();*/
                    });
        });

    }

    private Future<HttpResponse<Buffer>> obtenerToken() {
        String auth = "Basic " + this.accessBasic;
        MultiMap form = MultiMap.caseInsensitiveMultiMap();
        form.set("grant_type", "client_credentials");
        return this.client.postAbs(this.urlAccess)
                .putHeader("Content-Type", "application/x-www-form-urlencoded")
                .authentication(new UsernamePasswordCredentials(this.clientId, this.clientSecret))
                .sendForm(form);
    }

    public void setUrlGeneros(String urlGeneros) {
        this.urlGeneros = urlGeneros;
    }
    public void setUrlAccess(String urlAccess) {
        this.urlAccess = urlAccess;
    }
}
