package com.partymanager.mappers;

import com.partymanager.controllers.dtos.request.EventRequestDTO;
import com.partymanager.controllers.dtos.response.EventResponseDTO;
import com.partymanager.models.Event;
import com.partymanager.models.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    private final ServiceProviderMapper serviceProviderMapper;

    public EventMapper(ServiceProviderMapper serviceProviderMapper) {
        this.serviceProviderMapper = serviceProviderMapper;
    }

    public Event toEntity(EventRequestDTO dto, User user) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setName(dto.getName());
        event.setType(dto.getType());
        event.setDate(dto.getDate());
        event.setLocation(dto.getLocation());
        event.setUser(user);

        return event;
    }

    public EventResponseDTO toResponseDTO(Event entity) {
        if (entity == null) {
            return null;
        }

        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setDate(entity.getDate());
        dto.setLocation(entity.getLocation());

        if (entity.getServiceProviders() != null) {
            dto.setServiceProviders(entity.getServiceProviders().stream().map(serviceProviderMapper::toResponseDTO).collect(Collectors.toList()));
        } else {
            dto.setServiceProviders(Collections.emptyList());
        }

        return dto;
    }
}