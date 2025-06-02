package com.partymanager.controllers.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderResponseDTO {
    private Long id;
    private String text;
    private LocalDate date;
} 