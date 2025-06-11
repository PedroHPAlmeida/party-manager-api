package com.partymanager.services;

import com.partymanager.controllers.dtos.request.AuthRequestDTO;
import com.partymanager.controllers.dtos.request.RegisterRequestDTO;
import com.partymanager.controllers.dtos.response.AuthResponseDTO;
import com.partymanager.controllers.dtos.response.UserResponseDTO;
import com.partymanager.mappers.UserMapper;
import com.partymanager.models.User;
import com.partymanager.models.RefreshToken;
import com.partymanager.repositories.UserRepository;
import com.partymanager.security.JwtUtil;
import com.partymanager.services.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RefreshTokenService refreshTokenService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.userMapper = userMapper;
    }

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setBirthdate(LocalDate.now()); // TODO: Adjust if birthdate is required from frontend
        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser);
        return new AuthResponseDTO(userMapper.toResponseDTO(savedUser), token, refreshToken.getToken());
    }

    @Transactional
    public AuthResponseDTO login(AuthRequestDTO dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        refreshTokenService.deleteByUser(user); // Invalidate old refresh tokens
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthResponseDTO(userMapper.toResponseDTO(user), token, refreshToken.getToken());
    }

    public AuthResponseDTO refreshToken(String refreshTokenStr) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (refreshTokenService.isExpired(refreshToken)) {
            refreshTokenService.deleteByUser(refreshToken.getUser());
            throw new RuntimeException("Refresh token expired");
        }
        String token = jwtUtil.generateToken(refreshToken.getUser().getEmail());
        // Optionally, rotate refresh token:
        refreshTokenService.deleteByUser(refreshToken.getUser());
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(refreshToken.getUser());
        return new AuthResponseDTO(userMapper.toResponseDTO(refreshToken.getUser()), token, newRefreshToken.getToken());
    }

    public UserResponseDTO getCurrentUser(String token) {
        String email = jwtUtil.extractSubject(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponseDTO(user);
    }
} 