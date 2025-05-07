package com.partymanager.mappers;

import com.partymanager.controllers.dtos.request.ServiceProviderRequestDTO;
import com.partymanager.controllers.dtos.response.ServiceProviderResponseDTO;
import com.partymanager.models.Event;
import com.partymanager.models.ServiceProvider;
import org.springframework.stereotype.Component;

@Component
public class ServiceProviderMapper {
    public ServiceProvider toEntity(ServiceProviderRequestDTO dto, Event event) {
        if (dto == null) {
            return null;
        }

        ServiceProvider entity = new ServiceProvider();
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setEvent(event);

        return entity;
    }

    public ServiceProviderResponseDTO toResponseDTO(ServiceProvider entity) {
        if (entity == null) {
            return null;
        }

        ServiceProviderResponseDTO dto = new ServiceProviderResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());

        return dto;
    }
}