package com.partymanager.controllers;

import com.partymanager.controllers.dtos.request.AuthRequestDTO;
import com.partymanager.controllers.dtos.request.RegisterRequestDTO;
import com.partymanager.controllers.dtos.request.RefreshTokenRequestDTO;
import com.partymanager.controllers.dtos.response.AuthResponseDTO;
import com.partymanager.controllers.dtos.response.UserResponseDTO;
import com.partymanager.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and session management")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account and returns user info with tokens.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or email already registered")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.register(requestDTO));
    }

    @Operation(summary = "Login user", description = "Authenticates user and returns user info with tokens.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully", content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }

    @Operation(summary = "Get current authenticated user", description = "Returns the profile of the currently authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile returned", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String token = authHeader.substring(7);
        return ResponseEntity.ok(authService.getCurrentUser(token));
    }

    @Operation(summary = "Logout user", description = "Logs out the user and invalidates tokens (stateless, frontend should remove tokens).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged out successfully")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Stateless: just return 200, frontend should remove tokens
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Refresh JWT token", description = "Renews JWT using a valid refresh token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token refreshed successfully", content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.refreshToken(requestDTO.getRefreshToken()));
    }
} 