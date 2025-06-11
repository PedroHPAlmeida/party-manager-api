package com.partymanager.controllers.dtos.response;

import com.partymanager.controllers.dtos.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private UserResponseDTO user;
    private String token;
    private String refreshToken;
} 