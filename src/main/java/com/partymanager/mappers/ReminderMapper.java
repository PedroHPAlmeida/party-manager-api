package com.partymanager.mappers;

import com.partymanager.models.Reminder;
import com.partymanager.controllers.dtos.request.ReminderRequestDTO;
import com.partymanager.controllers.dtos.response.ReminderResponseDTO;

public class ReminderMapper {
    public static Reminder toEntity(ReminderRequestDTO dto) {
        Reminder reminder = new Reminder();
        reminder.setText(dto.getText());
        reminder.setDate(dto.getDate());
        return reminder;
    }

    public static ReminderResponseDTO toResponseDTO(Reminder reminder) {
        ReminderResponseDTO dto = new ReminderResponseDTO();
        dto.setId(reminder.getId());
        dto.setText(reminder.getText());
        dto.setDate(reminder.getDate());
        return dto;
    }
} 