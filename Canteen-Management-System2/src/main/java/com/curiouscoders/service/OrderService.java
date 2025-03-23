package com.curiouscoders.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curiouscoders.model.Employee;
import com.curiouscoders.model.MenuItem;
import com.curiouscoders.model.Order;
import com.curiouscoders.model.OrderMenuItem;
import com.curiouscoders.repository.EmployeeRepository;
import com.curiouscoders.repository.MenuItemRepository;
import com.curiouscoders.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Transactional
    public Order createOrder(List<MenuItem> menuItems) {  // Removed userId
        // **Assign an available employee automatically**
        Employee employee = employeeRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new IllegalStateException("No employees available"));

        // Calculate total price
        double totalPrice = 0;
        List<OrderMenuItem> processedOrderMenuItems = new ArrayList<>();

        for (MenuItem item : menuItems) {
            MenuItem menuItem = menuItemRepository.findById(item.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

            if (!menuItem.getAvailable()) {
                throw new IllegalArgumentException("Item " + menuItem.getName() + " is not available.");
            }

            // **Create OrderMenuItem**
            OrderMenuItem orderMenuItem = new OrderMenuItem();
            orderMenuItem.setMenuItem(menuItem);
            orderMenuItem.setQuantity(1); // Ensure this is set dynamically
            processedOrderMenuItems.add(orderMenuItem);

            totalPrice += menuItem.getPrice();
        }

        // Create and Save Order
        Order order = new Order();
        order.setCreatedBy(employee); // **Automatically assigned**
        order.setOrderMenuItems(processedOrderMenuItems);
        order.setTotalPrice(totalPrice);
        order.setStatus("CONFIRMED");
        order.setOrderTime(LocalDateTime.now());

        // **Set order reference in OrderMenuItems**
        for (OrderMenuItem orderMenuItem : processedOrderMenuItems) {
            orderMenuItem.setOrder(order);
        }

        return orderRepository.save(order);
    }
    
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }


    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }
}
