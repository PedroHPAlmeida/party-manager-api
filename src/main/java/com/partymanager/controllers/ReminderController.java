package com.partymanager.controllers;

import com.partymanager.controllers.dtos.request.ReminderRequestDTO;
import com.partymanager.controllers.dtos.response.ReminderResponseDTO;
import com.partymanager.services.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reminders", description = "APIs for managing user reminders")
@RestController
@RequestMapping("/reminders")
public class ReminderController {
    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @Operation(summary = "Get all reminders", description = "Returns a list of all reminders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of reminders successfully returned",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReminderResponseDTO.class))))
    })
    @GetMapping
    public List<ReminderResponseDTO> getAllReminders() {
        return reminderService.findAll();
    }

    @Operation(summary = "Create a new reminder", description = "Creates a new reminder with text and date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reminder successfully created",
            content = @Content(schema = @Schema(implementation = ReminderResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<ReminderResponseDTO> createReminder(
            @Parameter(description = "Reminder data", required = true)
            @RequestBody ReminderRequestDTO requestDTO) {
        return ResponseEntity.ok(reminderService.save(requestDTO));
    }

    @Operation(summary = "Delete a reminder", description = "Removes a reminder by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reminder successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Reminder not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(
            @Parameter(description = "Reminder ID", required = true)
            @PathVariable Long id) {
        reminderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 