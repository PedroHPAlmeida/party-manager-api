package com.partymanager.controllers;

import com.partymanager.controllers.dtos.request.ServiceProviderRequestDTO;
import com.partymanager.controllers.dtos.response.ServiceProviderResponseDTO;
import com.partymanager.services.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final ServiceProviderService serviceProviderService;

    @PostMapping("/{eventId}/service-providers")
    public ResponseEntity<ServiceProviderResponseDTO> addServiceProvider(
            @PathVariable Long eventId,
            @RequestBody ServiceProviderRequestDTO request) {

        ServiceProviderResponseDTO responseDTO =
                serviceProviderService.addServiceProviderToEvent(eventId, request);
        return ResponseEntity.ok(responseDTO);
    }
}