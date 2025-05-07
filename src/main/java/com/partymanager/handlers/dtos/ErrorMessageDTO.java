package com.partymanager.handlers.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErrorMessageDTO {
    private Integer status;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}