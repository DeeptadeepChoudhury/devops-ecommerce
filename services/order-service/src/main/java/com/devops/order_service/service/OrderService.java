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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Value("${USER_SERVICE_URL}")
    private String userServiceUrl;

    @Value("${PRODUCT_SERVICE_URL}")
    private String productServiceUrl;

    private final WebClient webClient;

    public OrderService(WebClient webClient) {
        this.webClient = webClient;
    }

    public OrderResponse placeOrder(OrderRequest request) {

        log.info("Placing order for userId={} productId={}", 
         request.getUserId(), request.getProductId());

        User user = webClient.get()
                .uri(userServiceUrl + "/users/{id}", request.getUserId())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        response.bodyToMono(String.class)
                                .map(body -> new ResourceNotFoundException(
                                        "User not found with ID: " + request.getUserId()))
                )
                .bodyToMono(User.class)
                .block();

        Product product = webClient.get()
                .uri(productServiceUrl + "/products/{id}", request.getProductId())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        response.bodyToMono(String.class)
                                .map(body -> new ResourceNotFoundException(
                                        "Product not found with ID: " + request.getProductId()))
                )
                .bodyToMono(Product.class)
                .block();

        log.info("Order placed successfully for userId={} productId={}",
         request.getUserId(), request.getProductId());
         
        return new OrderResponse(user, product, "Order placed successfully");
    }
}