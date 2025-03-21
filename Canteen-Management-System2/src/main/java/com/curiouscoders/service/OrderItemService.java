package com.curiouscoders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curiouscoders.model.MenuItem;
import com.curiouscoders.model.Order;
import com.curiouscoders.model.OrderItem;
import com.curiouscoders.repository.MenuItemRepository;
import com.curiouscoders.repository.OrderItemRepository;
import com.curiouscoders.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderItem createOrderItem(Long orderId, String menuItemName, int quantity) {
        // Find Menu Item by Name
        MenuItem menuItem = menuItemRepository.findByName(menuItemName)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        // Find Order by ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Create and Save OrderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(quantity);

        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Transactional
    public void deleteOrderItem(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new IllegalArgumentException("Order item not found");
        }
        orderItemRepository.deleteById(orderItemId);
    }
}
