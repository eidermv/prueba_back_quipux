package com.example.back.lista.application.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConsultarGeneroSpotifyTest {
    public static MockWebServer mockBackEnd;
    private ObjectMapper MAPPER = new ObjectMapper();
    @InjectMocks
    private ConsultarGeneroSpotify consultarGeneroSpotify;

    private String clientId = "b1c623a19a044396ba156b331ed1d25c";
    private String clientSecret = "6115df56feff4c1086a70d976faab988";

    final static Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

            switch (request.getPath()) {
                case "/login":
                    return new MockResponse()
                            .setResponseCode(200).setBody(new JsonObject().put("access_token", "qwertyuiop").toString());

                case "/generos":
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(new JsonObject().put("genres", new JsonArray()).toString());

            }
            return new MockResponse().setResponseCode(404);
        }
    };


    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.setDispatcher(dispatcher);
        mockBackEnd.start(8090);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        consultarGeneroSpotify.client = WebClient.create(Vertx.vertx());
        String baseUrl = String.format("http://%s:8090/", mockBackEnd.getHostName());
        consultarGeneroSpotify.setUrlGeneros(baseUrl + "generos");
        consultarGeneroSpotify.setUrlAccess(baseUrl + "login");
        ReflectionTestUtils.setField(consultarGeneroSpotify, "clientId", clientId);
        ReflectionTestUtils.setField(consultarGeneroSpotify, "clientSecret", clientSecret);
    }

    @Test
    void givenFieldConsultarGenero_whenExecute_thenResponseSucceed() throws Exception {

        Mono<ResponseEntity> responseEntityMono = consultarGeneroSpotify.execute();


        StepVerifier.create(responseEntityMono)
                .expectNext(ResponseEntity.status(200).body(new JsonObject().put("generos", new JsonArray()).toString()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/login", recordedRequest.getPath());
    }
}
