package com.partymanager.services.impl;

import com.partymanager.controllers.dtos.request.ServiceProviderRequestDTO;
import com.partymanager.controllers.dtos.response.ServiceProviderResponseDTO;
import com.partymanager.exception.exceptions.ResourceNotFoundException;
import com.partymanager.mappers.ServiceProviderMapper;
import com.partymanager.models.Event;
import com.partymanager.models.ServiceProvider;
import com.partymanager.repositories.EventRepository;
import com.partymanager.repositories.ServiceProviderRepository;
import com.partymanager.services.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final EventRepository eventRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final ServiceProviderMapper serviceProviderMapper;

    @Override
    @Transactional
    public ServiceProviderResponseDTO addServiceProviderToEvent(Long eventId, ServiceProviderRequestDTO requestDTO) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + eventId));
        ServiceProvider serviceProvider = serviceProviderMapper.toEntity(requestDTO, event);
        serviceProvider = serviceProviderRepository.save(serviceProvider);
        return serviceProviderMapper.toResponseDTO(serviceProvider);
    }
}