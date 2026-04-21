package com.devops.user_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getUsers() {
        return "List of users";
    }

    @PostMapping
    public String createUser() {
        return "User created";
    }
}
