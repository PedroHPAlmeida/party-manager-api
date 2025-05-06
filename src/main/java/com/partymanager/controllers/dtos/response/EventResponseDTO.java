package com.partymanager.controllers.dtos.response;

import com.partymanager.enums.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String eventName;
    private EventType eventType;
    private LocalDateTime date;
    private String location;
    private Long userId;
    private String userName;
    private List<ServiceProviderResponseDTO> serviceProviders;
}