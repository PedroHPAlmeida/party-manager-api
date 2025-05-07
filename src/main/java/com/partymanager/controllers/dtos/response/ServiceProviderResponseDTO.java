package com.partymanager.controllers.dtos.response;

import com.partymanager.models.enums.ServiceType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceProviderResponseDTO {
    private Long id;
    private ServiceType type;
    private String name;
}