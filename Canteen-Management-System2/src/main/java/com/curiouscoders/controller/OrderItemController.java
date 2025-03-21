package com.curiouscoders.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curiouscoders.model.OrderItem;
import com.curiouscoders.service.OrderItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

//    @PostMapping
//    public ResponseEntity<?> createOrderItem(@RequestBody Map<String, Object> requestBody) {
//        Long orderId = ((Number) requestBody.get("orderId")).longValue();
//        String menuItemName = (String) requestBody.get("menuItemName");
//        int quantity = (int) requestBody.get("quantity");
//
//        if (orderId == null || orderId <= 0) {
//            return ResponseEntity.badRequest().body("Invalid Order ID");
//        }
//        if (menuItemName == null || menuItemName.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Menu item name cannot be empty");
//        }
//        if (quantity <= 0) {
//            return ResponseEntity.badRequest().body("Quantity must be greater than zero");
//        }
//
//        OrderItem orderItem = orderItemService.createOrderItem(orderId, menuItemName, quantity);
//        return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
//    }
//
//    @PostMapping("/getByOrderId")
//    public ResponseEntity<?> getOrderItemsByOrderId(@RequestBody Map<String, Long> requestBody) {
//        Long orderId = requestBody.get("orderId");
//        if (orderId == null || orderId <= 0) {
//            return ResponseEntity.badRequest().body("Invalid Order ID");
//        }
//
//        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
//        return ResponseEntity.ok(orderItems);
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<?> deleteOrderItem(@RequestBody Map<String, Long> requestBody) {
//        Long orderItemId = requestBody.get("orderItemId");
//        if (orderItemId == null || orderItemId <= 0) {
//            return ResponseEntity.badRequest().body("Invalid Order Item ID");
//        }
//
//        orderItemService.deleteOrderItem(orderItemId);
//        return ResponseEntity.ok("Order item deleted successfully");
//    }
}