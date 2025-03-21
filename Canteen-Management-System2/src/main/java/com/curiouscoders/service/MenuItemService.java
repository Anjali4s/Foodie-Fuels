package com.curiouscoders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curiouscoders.model.Employee;
import com.curiouscoders.model.MenuItem;
import com.curiouscoders.repository.EmployeeRepository;
import com.curiouscoders.repository.MenuItemRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public MenuItem createMenuItem(String name, String category, Double price, Boolean available, Long employeeId, String imageUrl) {
        if (menuItemRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Menu item already exists!");
        }

        Employee createdBy = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        MenuItem menuItem = new MenuItem(name, category, price, available, imageUrl, createdBy);
        return menuItemRepository.save(menuItem);
    }

    public MenuItem getMenuItemByName(String name) {
        return menuItemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem updateMenuItem(String name, String category, Double price, Boolean available, String imageUrl) {
        MenuItem menuItem = getMenuItemByName(name);

        if (category != null && !category.trim().isEmpty()) {
            menuItem.setCategory(category);
        }
        if (price != null && price > 0) {
            menuItem.setPrice(price);
        }
        if (available != null) {
            menuItem.setAvailable(available);
        }
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            menuItem.setImageUrl(imageUrl);
        }

        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(String name) {
        MenuItem menuItem = getMenuItemByName(name);
        menuItemRepository.delete(menuItem);
    }
}