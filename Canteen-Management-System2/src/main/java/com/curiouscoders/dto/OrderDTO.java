package com.curiouscoders.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.curiouscoders.model.Employee;
import com.curiouscoders.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
	private Long id; // Order ID
    private Long userId; // ID of the user who placed the order
    private double totalPrice; // Total price of the order
    private String status; // Status of the order (e.g., Pending, Completed, Cancelled)
    private Long createdById; // ID of the employee who processed the order (change to ID, not object)
    private LocalDateTime orderTime; // Order time
    private List<OrderItemDTO> orderItems; // List of order items as DTOs
    
}