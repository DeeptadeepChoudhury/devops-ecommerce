package com.devops.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/")
    public String home() {
        return "User Service is running";
    }

    @GetMapping("/users")
    public String users() {
        return "List of users";
    }
}
