package com.ParameterizedTypeReference.client;

import com.ParameterizedTypeReference.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class FakeStoreClient {
    private final WebClient webClient;
    private final String baseUrl;

    //Configuration in WebClient
    public FakeStoreClient(@Value("${fakestore.base-url}") String baseUrl){
        this.baseUrl = Objects.requireNonNull(baseUrl);
        this.webClient = WebClient.builder()
                .baseUrl(this.baseUrl)
                .build();
    }

    public Mono<List<Product>> getAllProducts(){
        return webClient.get()
                .uri("/products")
                .retrieve()
                .onStatus(HttpStatusCode::isError, res ->
                        res.createException().flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<List<Product>>() {
                });
    }
}
