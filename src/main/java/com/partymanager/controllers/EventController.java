package com.partymanager.controllers;

import com.partymanager.controllers.dtos.request.EventRequestDTO;
import com.partymanager.controllers.dtos.request.ServiceProviderRequestDTO;
import com.partymanager.controllers.dtos.response.EventResponseDTO;
import com.partymanager.controllers.dtos.response.ServiceProviderResponseDTO;
import com.partymanager.services.EventService;
import com.partymanager.services.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final ServiceProviderService serviceProviderService;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO requestDTO) {
        EventResponseDTO responseDTO = eventService.createEvent(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable Long id) {
        EventResponseDTO responseDTO = eventService.getEventById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @RequestBody EventRequestDTO requestDTO) {
        EventResponseDTO responseDTO = eventService.updateEvent(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/service-providers")
    public ResponseEntity<ServiceProviderResponseDTO> addServiceProvider(
            @PathVariable Long eventId,
            @RequestBody ServiceProviderRequestDTO request) {
        ServiceProviderResponseDTO responseDTO =
                serviceProviderService.addServiceProviderToEvent(eventId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{eventId}/service-providers/{providerId}")
    public ResponseEntity<Void> removeServiceProvider(
            @PathVariable Long eventId,
            @PathVariable Long providerId) {
        serviceProviderService.removeServiceProviderFromEvent(eventId, providerId);
        return ResponseEntity.noContent().build();
    }
}