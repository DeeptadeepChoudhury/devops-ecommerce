package com.devops.order_service.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getOrders() {
        return "List of orders";
    }

    @PostMapping
    public String createOrder() {

        String users = restTemplate.getForObject(
                "http://user-service:8080/users",
                String.class
        );

        String products = restTemplate.getForObject(
                "http://product-service:8082/products",
                String.class
        );

        return "Order created using -> " + users + " & " + products;
    }
}
