package com.ParameterizedTypeReference.controller;

import com.ParameterizedTypeReference.client.FakeStoreClient;
import com.ParameterizedTypeReference.model.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class DemoController {

    private final FakeStoreClient client;

    public DemoController(FakeStoreClient client){
        this.client = client;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Product>> getAllProducts(){
        return client.getAllProducts();
    }
}
