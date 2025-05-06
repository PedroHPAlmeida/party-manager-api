package com.partymanager.services;

import com.partymanager.controllers.dtos.request.EventRequestDTO;
import com.partymanager.controllers.dtos.response.EventResponseDTO;

public interface EventService {
    EventResponseDTO createEvent(EventRequestDTO requestDTO);
    EventResponseDTO updateEvent(Long eventId, EventRequestDTO requestDTO);
    EventResponseDTO getEventById(Long eventId);
    void deleteEvent(Long eventId);
}