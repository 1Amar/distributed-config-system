package com.amar.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    private final WebClient.Builder webClientBuilder;

    public String processUser(String userId) {
    	
    	
//        // Simulate fetching user
//        String user = "User-" + userId;
//
//        // Call OrderService
//        List<String> orders = webClientBuilder.build()
//                .get()
//                .uri("http://localhost:8082/orders/user/{userId}", userId)
//                .retrieve()
//                .bodyToFlux(String.class)
//                .collectList()
//                .block();  // Blocking for simplicity; reactive version can be done too
//
//        return "User: " + user + ", Orders: " + orders;
        
        return processUserAsync(userId).block(Duration.ofSeconds(10));
    }
    
    public Mono<String> processUserAsync(String userId) {
        log.info("🔄 Processing user ID: {}", userId);
        
        String user = "User-" + userId;

        return webClientBuilder.build()
                .get()
                .uri("http://order-service/orders/user/{userId}", userId)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorResume(ex -> {
                    log.error("❌ Failed to fetch orders for user {}: {}", userId, ex.getMessage());
                    return Mono.just("[]");
                })
                .map(orders -> "User: " + user + ", Orders: " + orders)
                .doOnSuccess(result -> log.info("✅ Successfully processed user: {}", userId))
                .doOnError(ex -> log.error("❌ Error processing user {}: {}", userId, ex.getMessage()));
    }

}

