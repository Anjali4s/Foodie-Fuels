package com.curiouscoders.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curiouscoders.model.MenuItem;
import com.curiouscoders.service.MenuItemService;

@RestController
@RequestMapping("/api/menu-items")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class MenuItemController {
    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<?> createMenuItem(@RequestBody Map<String, Object> requestBody) {
        try {
            String name = (String) requestBody.get("name");
            String category = (String) requestBody.get("category");
            Double price = Double.parseDouble(requestBody.get("price").toString());
            Boolean available = (Boolean) requestBody.get("available");
            String imageUrl = (String) requestBody.get("imageUrl");
            Long employeeId = Long.parseLong(requestBody.get("employeeId").toString());

            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Item name cannot be empty");
            }
            if (category == null || category.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Category cannot be empty");
            }
            if (price == null || price <= 0) {
                return ResponseEntity.badRequest().body("Price must be greater than zero");
            }

            MenuItem savedMenuItem = menuItemService.createMenuItem(name, category, price, available, employeeId, imageUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMenuItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        }
    }
    
    


    @PostMapping("/getByName")
    public ResponseEntity<?> getMenuItemByName(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Item name cannot be empty");
        }

        MenuItem menuItem = menuItemService.getMenuItemByName(name);
        return ResponseEntity.ok(menuItem);
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMenuItem(@RequestBody Map<String, Object> requestBody) {
        try {
            String name = (String) requestBody.get("name");
            String category = (String) requestBody.get("category");
            Double price = Double.parseDouble(requestBody.get("price").toString());
            Boolean available = (Boolean) requestBody.get("available");
            String imageUrl = (String) requestBody.get("imageUrl");

            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Item name cannot be empty");
            }

            MenuItem updatedMenuItem = menuItemService.updateMenuItem(name, category, price, available, imageUrl);
            return ResponseEntity.ok(updatedMenuItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMenuItem(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Item name cannot be empty");
        }

        menuItemService.deleteMenuItem(name);
        return ResponseEntity.ok("Menu item deleted successfully");
    }
}