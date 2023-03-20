package com.darkstar.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarsShuttleErrorResponse {
    private String errorMessage;
    private Integer errorCode;
}