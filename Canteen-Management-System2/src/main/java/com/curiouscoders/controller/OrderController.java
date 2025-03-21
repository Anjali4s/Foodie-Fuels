package com.curiouscoders.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curiouscoders.model.MenuItem;
import com.curiouscoders.model.Order;
import com.curiouscoders.model.OrderItem;
import com.curiouscoders.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> requestBody) {
        try {
            Long userId = ((Number) requestBody.get("userId")).longValue();
            List<Map<String, Object>> orderItemsData = (List<Map<String, Object>>) requestBody.get("orderItems");

            if (userId == null || userId <= 0) {
                return ResponseEntity.badRequest().body("Invalid User ID");
            }
            if (orderItemsData == null || orderItemsData.isEmpty()) {
                return ResponseEntity.badRequest().body("Order must contain at least one item");
            }

            // Convert orderItemsData to OrderItem objects
            List<OrderItem> orderItems = new ArrayList<>();
            for (Map<String, Object> itemData : orderItemsData) {
                OrderItem item = new OrderItem();
                item.setMenuItem(new MenuItem());
                item.getMenuItem().setId(((Number) itemData.get("menuItemId")).longValue());
                item.setQuantity(((Number) itemData.get("quantity")).intValue());
                orderItems.add(item);
            }

            Order order = orderService.createOrder(userId, orderItems);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }

    @PostMapping("/getByUser")
    public ResponseEntity<?> getOrdersByUser(@RequestBody Map<String, Long> requestBody) {
        Long userId = requestBody.get("userId");
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().body("Invalid User ID");
        }

        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/getById")
    public ResponseEntity<?> getOrderById(@RequestBody Map<String, Long> requestBody) {
        Long orderId = requestBody.get("orderId");
        if (orderId == null || orderId <= 0) {
            return ResponseEntity.badRequest().body("Invalid Order ID");
        }

        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/updateStatus")
    public ResponseEntity<?> updateOrderStatus(@RequestBody Map<String, Object> requestBody) {
        Long orderId = ((Number) requestBody.get("orderId")).longValue();
        String status = (String) requestBody.get("status");

        if (orderId == null || orderId <= 0) {
            return ResponseEntity.badRequest().body("Invalid Order ID");
        }
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Order status cannot be empty");
        }

        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestBody Map<String, Long> requestBody) {
        Long orderId = requestBody.get("orderId");
        if (orderId == null || orderId <= 0) {
            return ResponseEntity.badRequest().body("Invalid Order ID");
        }

        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }
}