package com.partymanager.controllers.dtos.request;

import com.partymanager.enums.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventRequestDTO {
    private String eventName;

    private EventType eventType;

    private LocalDateTime date;

    private String location;

    private Long userId;
}