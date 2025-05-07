package com.partymanager.controllers.dtos.request;

import com.partymanager.models.enums.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventRequestDTO {
    private String name;
    private EventType type;
    private LocalDateTime date;
    private String location;
    private Long userId;
}