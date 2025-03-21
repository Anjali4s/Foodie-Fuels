package com.curiouscoders.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;            // Unique ID of the employee
    private String role;        // Employee's role (e.g., Admin, Staff)
    private boolean active;     // Active status
    private Long userId;        // ID of the associated User
    private Long createdById;   // ID of the employee who created this record
}

