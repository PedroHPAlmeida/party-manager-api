package com.partymanager.services;

import com.partymanager.controllers.dtos.request.ReminderRequestDTO;
import com.partymanager.controllers.dtos.response.ReminderResponseDTO;
import com.partymanager.mappers.ReminderMapper;
import com.partymanager.models.Reminder;
import com.partymanager.repositories.ReminderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public List<ReminderResponseDTO> findAll() {
        return reminderRepository.findAll().stream()
                .map(ReminderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReminderResponseDTO save(ReminderRequestDTO dto) {
        Reminder reminder = ReminderMapper.toEntity(dto);
        Reminder saved = reminderRepository.save(reminder);
        return ReminderMapper.toResponseDTO(saved);
    }

    public void deleteById(Long id) {
        reminderRepository.deleteById(id);
    }
} 