package com.curiouscoders.service;

import org.springframework.stereotype.Component;

import com.curiouscoders.dto.EmployeeDTO;
import com.curiouscoders.dto.UserDTO;
import com.curiouscoders.model.Employee;
import com.curiouscoders.model.User;

@Component
public class ConversionUtils {

    // Convert UserDTO to User (DTO -> Model)
    public User convertToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Assuming password comes hashed
        user.setActive(userDTO.getActive());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        return user;
    }

    // Convert User to UserDTO (Model -> DTO)
    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setActive(user.getActive());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    // Convert EmployeeDTO to Employee (DTO -> Model)
    /*public Employee convertToEmployeeEntity(EmployeeDTO employeeDTO, User user) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setRole(employeeDTO.getRole());
        employee.setActive(employeeDTO.isActive());
        employee.setUser(user); // Associate with user
        return employee;
    }

    // Convert Employee to EmployeeDTO (Model -> DTO)
    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setActive(employee.isActive());
        employeeDTO.setUserId(employee.getUser().getId()); // Associate user ID
        return employeeDTO;
    } */

    // Convert MenuDTO to Menu (DTO -> Model)
    /*public MenuItem convertToMenuEntity(MenuDTO menuDTO, Employee createdBy) {
    	MenuItem menu = new MenuItem();
        menu.setId(menuDTO.getId());
        menu.setItemName(menuDTO.getItemName());
        menu.setCategory(menuDTO.getCategory());
        menu.setPrice(menuDTO.getPrice());
        menu.setAvailable(menuDTO.isAvailable());
        menu.setCreatedBy(createdBy); // Associate with the employee who created the menu
        return menu;
    }

    // Convert Menu to MenuDTO (Model -> DTO)
    public MenuDTO convertToMenuDTO(MenuItem menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menu.getId());
        menuDTO.setItemName(menu.getItemName());
        menuDTO.setCategory(menu.getCategory());
        menuDTO.setPrice(menu.getPrice());
        menuDTO.setAvailable(menu.isAvailable());
        menuDTO.setCreatedById(menu.getCreatedBy().getId()); // Associate createdBy ID
        return menuDTO;
    }   */
    
// // Convert OrderDTO to Order (DTO -> Entity)
//    public Order convertToOrderEntity(OrderDTO orderDTO) {
//        Order order = new Order();
//        order.setId(orderDTO.getId());
//        order.setId(orderDTO.getUserId());
//        order.setTotalPrice(orderDTO.getTotalPrice());
//        order.setStatus(orderDTO.getStatus());
//        order.setCreatedBy(orderDTO.getCreatedBy());
//        order.setOrderItems(orderDTO.getOrderItems());
//        
//        
        
//        if (orderDTO.getOrderItems() != null) {
//            List<OrderItem> orderItems = orderDTO.getOrderItems()
//                                                 .stream()
//                                                 .map(this::convertToOrderItemEntity)
//                                                 .collect(Collectors.toList());
//            order.setOrderItems(orderItems);
//        }
//
//        return order;
//    }

    // Convert OrderItemDTO to OrderItem (DTO -> Entity)
//   

    
}
