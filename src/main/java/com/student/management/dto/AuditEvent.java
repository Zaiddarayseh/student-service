package com.student.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuditEvent implements Serializable {

    private String name;
    private String action;
    private String description;
    private LocalDateTime createdAt;
}
