package com.curiouscoders.dto;

import com.curiouscoders.model.MenuItem;
import com.curiouscoders.model.Order;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
	private Long id;
    private int quantity;
    private MenuItem menuItem; // The menu item
    private Order order; // The associated order
}

