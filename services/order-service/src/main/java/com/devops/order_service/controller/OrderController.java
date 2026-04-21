package com.devops.order_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/")
    public String home() {
        return "Order Service is running";
    }

    @GetMapping("/orders")
    public String orders() {
        return "List of orders";
    }
}
