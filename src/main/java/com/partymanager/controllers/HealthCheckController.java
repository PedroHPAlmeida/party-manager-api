package com.partymanager.controllers;

import com.partymanager.controllers.dtos.response.HealthCheckResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "System Status", description = "Endpoints for monitoring system health")
public class HealthCheckController {

    @GetMapping("/health-check")
    @Operation(
            summary = "Check service health status",
            description = "Returns the current health status of the application. Used by monitoring tools and load balancers to verify service availability."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Service is operational",
                    content = @Content(schema = @Schema(implementation = HealthCheckResponseDTO.class))
            )
    })
    public HealthCheckResponseDTO healthCheck() {
        return new HealthCheckResponseDTO("UP");
    }
}