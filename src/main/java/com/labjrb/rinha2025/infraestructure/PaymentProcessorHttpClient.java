package com.labjrb.rinha2025.infraestructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PaymentProcessorHttpClient {

    private final HttpClient httpClient;
    private final String baseUrl;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public PaymentProcessorHttpClient(String baseUrl){
        this.baseUrl    = baseUrl;
        this.httpClient = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofMillis(200))
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
    }

    public String get(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(this.baseUrl + path))
                .GET()
                .build();

        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String post(String path, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(this.baseUrl + path))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String toStringJson(Object obj) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(obj);
    }

}
