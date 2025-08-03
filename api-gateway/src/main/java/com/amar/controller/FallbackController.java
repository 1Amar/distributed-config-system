package com.amar.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/users")
    public ResponseEntity<String> userServiceFallback() {
        return ResponseEntity.ok("User Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/orders")
    public ResponseEntity<String> orderServiceFallback() {
        return ResponseEntity.ok("Order Service is currently unavailable. Please try again later.");
    }
}

