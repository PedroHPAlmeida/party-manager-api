package com.partymanager.services;

import com.partymanager.controllers.dtos.request.ServiceProviderRequestDTO;
import com.partymanager.controllers.dtos.response.ServiceProviderResponseDTO;

public interface ServiceProviderService {
    ServiceProviderResponseDTO addServiceProviderToEvent(Long eventId, ServiceProviderRequestDTO requestDTO);
    void removeServiceProviderFromEvent(Long eventId, Long serviceProviderId);
}