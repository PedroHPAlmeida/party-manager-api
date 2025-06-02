package com.partymanager.controllers.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderRequestDTO {
    private String text;
    private LocalDate date;
} 