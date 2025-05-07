package com.partymanager.mappers;

import com.partymanager.controllers.dtos.request.UserRequestDTO;
import com.partymanager.controllers.dtos.response.UserResponseDTO;
import com.partymanager.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO requestDTO) {
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setBirthdate(requestDTO.getBirthdate());
        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setBirthdate(user.getBirthdate());
        return responseDTO;
    }

    public void updateEntityFromDTO(UserRequestDTO requestDTO, User user) {
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setBirthdate(requestDTO.getBirthdate());
    }
}