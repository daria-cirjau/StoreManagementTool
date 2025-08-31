package com.store.auth.controller;

import com.store.auth.entity.Role;
import com.store.auth.entity.User;
import com.store.auth.entity.dto.UserDTO;
import com.store.auth.service.AuthService;
import com.store.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserDTO dto) {
        authService.register(dto.username(), dto.password());
        return ResponseEntity.ok(ApiResponse.ofMessage("User registered: " + dto.username(), 200));
    }

    @PostMapping("/grantRole")
    public ResponseEntity<ApiResponse> grantRole(@RequestParam String username, @RequestParam String role) {
        authService.grantRole(username, role);
        return ResponseEntity.ok(ApiResponse.ofMessage("Granted role " + role + " to " + username, 200));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = authService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.ofData(users, 200));
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> getAllRoles() {
        List<Role> roles = authService.getAllRoles();
        return ResponseEntity.ok(ApiResponse.ofData(roles, 200));
    }
}
