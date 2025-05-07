package com.partymanager.controllers.dtos.request;

import com.partymanager.models.enums.ServiceType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceProviderRequestDTO {
    private ServiceType type;
    private String name;

}