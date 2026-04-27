package com.devops.order_service.dto;

public class OrderResponse {

    private User user;
    private Product product;
    private String message;

    public OrderResponse(User user, Product product, String message) {
        this.user = user;
        this.product = product;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public String getMessage() {
        return message;
    }
}
