package com.curiouscoders.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curiouscoders.model.Employee;
import com.curiouscoders.model.MenuItem;
import com.curiouscoders.model.Order;
import com.curiouscoders.model.OrderItem;
import com.curiouscoders.model.User;
import com.curiouscoders.repository.EmployeeRepository;
import com.curiouscoders.repository.MenuItemRepository;
import com.curiouscoders.repository.OrderRepository;
import com.curiouscoders.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	@Autowired
    private OrderRepository orderRepository;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private EmployeeRepository employeeRepository;
	@Autowired
    private MenuItemRepository menuItemRepository;

    @Transactional
    public Order createOrder(Long userId, List<OrderItem> orderItems) {
        // Fetch User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // **Assign an available employee automatically**
        Employee employee = employeeRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new IllegalStateException("No employees available"));

        // Calculate total price
        double totalPrice = 0;
        List<OrderItem> processedOrderItems = new ArrayList<>();

        for (OrderItem item : orderItems) {
            MenuItem menuItem = menuItemRepository.findById(item.getMenuItem().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
            
            if (!menuItem.getAvailable()) {
                throw new IllegalArgumentException("Item " + menuItem.getName() + " is not available.");
            }
            

            double itemTotalPrice = menuItem.getPrice() * item.getQuantity();
            totalPrice += itemTotalPrice;

            // Ensure each order item is correctly linked
            item.setMenuItem(menuItem);
            item.setPrice(menuItem.getPrice());
            item.setOrder(null); // Will be linked when order is saved
            processedOrderItems.add(item);
        }

        // Create and Save Order
        Order order = new Order();
        order.setUser(user);
        order.setCreatedBy(employee); // **Automatically assigned**
        order.setOrderItems(processedOrderItems);
        order.setTotalPrice(totalPrice);
        order.setStatus("CONFIRMED");
        order.setOrderTime(LocalDateTime.now());

        return orderRepository.save(order);
    }

    
    
//    public Order createOrder(Long userId, Long employeeId, List<OrderItem> orderItems) {
//        // Find User by ID
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        // Find Employee by ID
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
//
//        // Calculate total price
//        double totalPrice = orderItems.stream()
//                .mapToDouble(orderItem -> orderItem.getMenuItem().getPrice() * orderItem.getQuantity())
//                .sum();
//
//        // Create and Save Order
//        Order order = new Order();
//        order.setUser(user);
//        order.setCreatedBy(employee);
//        order.setOrderItems(orderItems);
//        order.setTotalPrice(totalPrice);
//        order.setStatus("CONFIRMED");
//
//        return orderRepository.save(order);
//    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
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
