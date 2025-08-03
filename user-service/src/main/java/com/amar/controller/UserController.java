package com.amar.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amar.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@GetMapping
	public List<String> getUsers() {
		log.info("👥 Fetching all users");
		return List.of("Amar", "Pooja", "Sita");
	}

	@GetMapping("/{id}")
	public String getUserById(@PathVariable String id) {
		log.info("🔍 Fetching user by ID: {}", id);
		return "User-" + id;
	}

	@GetMapping("/process/{id}")
	public ResponseEntity<String> processUser(@PathVariable String id) {
		log.info("⚙️ Processing user request for ID: {}", id);
		return ResponseEntity.ok(userService.processUser(id));
	}
}
