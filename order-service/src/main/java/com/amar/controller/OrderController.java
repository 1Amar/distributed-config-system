package com.amar.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @GetMapping
    public List<String> getOrders() {
    	 log.info("📦 Fetching all orders");
        return List.of("Order-100", "Order-101");
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable String id) {
    	log.info("🔍 Fetching order by ID: {}", id);
        return "Order-" + id;
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<String>> getOrdersByUserId(@PathVariable String userId) {
        // Dummy response
    	log.info("👤 Fetching orders for user ID: {}", userId);
        List<String> orders = List.of("order-1 for user " + userId, "order-2 for user " + userId);
        return ResponseEntity.ok(orders);
    }
}

