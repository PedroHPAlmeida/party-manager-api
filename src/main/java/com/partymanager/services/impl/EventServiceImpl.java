package com.partymanager.services.impl;

import com.partymanager.controllers.dtos.request.EventRequestDTO;
import com.partymanager.controllers.dtos.response.EventResponseDTO;
import com.partymanager.exception.exceptions.ResourceNotFoundException;
import com.partymanager.mappers.EventMapper;
import com.partymanager.models.Event;
import com.partymanager.models.User;
import com.partymanager.repositories.EventRepository;
import com.partymanager.repositories.UserRepository;
import com.partymanager.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventResponseDTO createEvent(EventRequestDTO requestDTO) {
        // Busca o usuário pelo ID
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + requestDTO.getUserId()));

        // Converte DTO para entidade
        Event event = eventMapper.toEntity(requestDTO, user);

        // Inicializa a lista de provedores de serviço (se necessário)
        if (event.getServiceProviders() == null) {
            event.setServiceProviders(new ArrayList<>());
        }

        // Salva o evento
        event = eventRepository.save(event);

        // Retorna o DTO de resposta
        return eventMapper.toResponseDTO(event);
    }

    @Override
    @Transactional
    public EventResponseDTO updateEvent(Long eventId, EventRequestDTO requestDTO) {
        // Busca o evento existente
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + eventId));

        // Verifica se o usuário mudou
        if (!existingEvent.getUser().getId().equals(requestDTO.getUserId())) {
            User newUser = userRepository.findById(requestDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + requestDTO.getUserId()));
            existingEvent.setUser(newUser);
        }

        // Atualiza os campos do evento
        existingEvent.setEventName(requestDTO.getEventName());
        existingEvent.setEventType(requestDTO.getEventType());
        existingEvent.setDate(requestDTO.getDate());
        existingEvent.setLocation(requestDTO.getLocation());

        // Salva as alterações
        existingEvent = eventRepository.save(existingEvent);

        // Retorna o DTO de resposta
        return eventMapper.toResponseDTO(existingEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponseDTO getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + eventId));

        return eventMapper.toResponseDTO(event);
    }

    @Override
    @Transactional
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + eventId));

        eventRepository.delete(event);
    }
}