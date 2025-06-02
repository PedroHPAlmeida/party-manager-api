package com.partymanager.controllers;

import com.partymanager.controllers.dtos.request.ReminderRequestDTO;
import com.partymanager.controllers.dtos.response.ReminderResponseDTO;
import com.partymanager.services.ReminderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
public class ReminderController {
    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping
    public List<ReminderResponseDTO> getAllReminders() {
        return reminderService.findAll();
    }

    @PostMapping
    public ResponseEntity<ReminderResponseDTO> createReminder(@RequestBody ReminderRequestDTO requestDTO) {
        return ResponseEntity.ok(reminderService.save(requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 