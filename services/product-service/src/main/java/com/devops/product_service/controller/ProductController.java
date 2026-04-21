package com.devops.product_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/")
    public String home() {
        return "Product Service is running";
    }

    @GetMapping("/products")
    public String products() {
        return "List of products";
    }
}
