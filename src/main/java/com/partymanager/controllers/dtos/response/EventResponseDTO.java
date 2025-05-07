package com.partymanager.controllers.dtos.response;

import com.partymanager.models.enums.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String name;
    private EventType type;
    private LocalDateTime date;
    private String location;
    private List<ServiceProviderResponseDTO> serviceProviders;
}