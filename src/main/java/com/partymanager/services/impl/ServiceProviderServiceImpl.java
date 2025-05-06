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
        // Busca o evento pelo ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + eventId));

        // Converte DTO para entidade usando o mapper
        ServiceProvider serviceProvider = serviceProviderMapper.toEntity(requestDTO, event);

        // Salva a entidade
        serviceProvider = serviceProviderRepository.save(serviceProvider);

        // Adiciona à lista de serviceProviders do evento (importante para manter a consistência em memória)
        // Isso não afeta o banco diretamente, pois o mapeamento é controlado pelo ServiceProvider.event
        event.getServiceProviders().add(serviceProvider);

        // Converte a entidade salva para DTO de resposta usando o mapper
        return serviceProviderMapper.toResponseDTO(serviceProvider);
    }

    @Override
    @Transactional
    public void removeServiceProviderFromEvent(Long eventId, Long serviceProviderId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + eventId));

        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(() -> new ResourceNotFoundException("Provedor de serviço não encontrado com ID: " + serviceProviderId));

        // Verifica se o provedor pertence ao evento
        if (!serviceProvider.getEvent().getId().equals(event.getId())) {
            throw new IllegalArgumentException("O provedor de serviço não pertence a este evento");
        }

        // Remove o provedor da lista em memória
        event.getServiceProviders().remove(serviceProvider);

        // Remove do banco de dados
        serviceProviderRepository.delete(serviceProvider);
    }
}