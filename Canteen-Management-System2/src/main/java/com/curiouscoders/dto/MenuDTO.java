package com.curiouscoders.dto;

import lombok.Data;

@Data
public class MenuDTO {

    private Long id;           // Unique identifier for the menu item
    private String itemName;   // Name of the menu item
    private String category;   // Category of the menu item
    private Double price;      // Price of the menu item
    private Boolean active; // Availability status
    private Long createdById;  // ID of the employee who created the menu item
}