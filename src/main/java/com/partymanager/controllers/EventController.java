package com.partymanager.controllers;

import com.partymanager.controllers.dtos.request.EventRequestDTO;
import com.partymanager.controllers.dtos.request.ServiceProviderRequestDTO;
import com.partymanager.controllers.dtos.response.EventResponseDTO;
import com.partymanager.controllers.dtos.response.ServiceProviderResponseDTO;
import com.partymanager.services.EventService;
import com.partymanager.services.ServiceProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Tag(name = "Events", description = "APIs for managing events and their service providers")
public class EventController {

    private final ServiceProviderService serviceProviderService;
    private final EventService eventService;

    @PostMapping
    @Operation(summary = "Create a new event", description = "Creates a new event with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully",
                    content = @Content(schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<EventResponseDTO> createEvent(
            @Parameter(description = "Event details", required = true) @RequestBody EventRequestDTO requestDTO) {
        EventResponseDTO responseDTO = eventService.createEvent(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID", description = "Retrieves event details by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found",
                    content = @Content(schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<EventResponseDTO> getEvent(
            @Parameter(description = "Event ID", required = true) @PathVariable Long id) {
        EventResponseDTO responseDTO = eventService.getEventById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an event", description = "Updates an existing event with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully",
                    content = @Content(schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<EventResponseDTO> updateEvent(
            @Parameter(description = "Event ID", required = true) @PathVariable Long id,
            @Parameter(description = "Updated event details", required = true) @RequestBody EventRequestDTO requestDTO) {
        EventResponseDTO responseDTO = eventService.updateEvent(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event", description = "Deletes an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<Void> deleteEvent(
            @Parameter(description = "Event ID", required = true) @PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/service-providers")
    @Operation(summary = "Add service provider to event",
            description = "Adds a new service provider to an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Service provider added successfully",
                    content = @Content(schema = @Schema(implementation = ServiceProviderResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ServiceProviderResponseDTO> addServiceProvider(
            @Parameter(description = "Event ID", required = true) @PathVariable Long id,
            @Parameter(description = "Service provider details", required = true) @RequestBody ServiceProviderRequestDTO request) {
        ServiceProviderResponseDTO responseDTO = serviceProviderService.addServiceProviderToEvent(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{id}/service-providers/{providerId}")
    @Operation(summary = "Remove service provider from event",
            description = "Removes a service provider from an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Service provider removed successfully"),
            @ApiResponse(responseCode = "404", description = "Event or service provider not found")
    })
    public ResponseEntity<Void> removeServiceProvider(
            @Parameter(description = "Event ID", required = true) @PathVariable Long id,
            @Parameter(description = "Service provider ID", required = true) @PathVariable Long providerId) {
        serviceProviderService.removeServiceProviderFromEvent(id, providerId);
        return ResponseEntity.noContent().build();
    }
}