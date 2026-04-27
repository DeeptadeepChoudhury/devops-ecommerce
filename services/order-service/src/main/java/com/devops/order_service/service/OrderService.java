package com.devops.order_service.service;

import com.devops.order_service.exception.ResourceNotFoundException;
import com.devops.order_service.dto.OrderRequest;
import com.devops.order_service.dto.OrderResponse;
import com.devops.order_service.dto.User;
import com.devops.order_service.dto.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderService {

    private final WebClient webClient;

    public OrderService(WebClient webClient) {
        this.webClient = webClient;
    }

    public OrderResponse placeOrder(OrderRequest request) {

        User user = webClient.get()
                .uri("http://user-service:8080/users/{id}", request.getUserId())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        response.bodyToMono(String.class)
                                .map(body -> new ResourceNotFoundException(
                                        "User not found with ID: " + request.getUserId()))
                )
                .bodyToMono(User.class)
                .block();

        Product product = webClient.get()
                .uri("http://product-service:8082/products/{id}", request.getProductId())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        response.bodyToMono(String.class)
                                .map(body -> new ResourceNotFoundException(
                                        "Product not found with ID: " + request.getProductId()))
                )
                .bodyToMono(Product.class)
                .block();

        return new OrderResponse(user, product, "Order placed successfully");
    }
}