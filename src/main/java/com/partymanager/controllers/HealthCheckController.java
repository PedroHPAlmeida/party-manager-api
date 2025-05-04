package com.partymanager.controllers;

import com.partymanager.controllers.dtos.response.HealthCheckResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {
    @GetMapping("/health-check")
    public HealthCheckResponseDTO healthCheck() {
        return new HealthCheckResponseDTO("UP");
    }
}
