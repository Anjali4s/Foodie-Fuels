package com.curiouscoders.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curiouscoders.model.Employee;
import com.curiouscoders.model.User;
import com.curiouscoders.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to CMS !!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> createEmployee(@RequestBody Map<String, Object> requestBody) {
        try {
            String firstName = (String) requestBody.get("firstName");
            String lastName = (String) requestBody.get("lastName");
            String email = (String) requestBody.get("email");
            String role = (String) requestBody.get("role");

            if (firstName == null || firstName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("First name cannot be empty");
            }
            if (lastName == null || lastName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Last name cannot be empty");
            }
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email cannot be empty");
            }

            // Create User entity
            User user = new User();
            user.setName(firstName + " " + lastName);
            user.setEmail(email);
            user.setPassword("defaultPassword"); // Set a default password
            user.setActive(true);

            // Create Employee entity
            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setRole(role);
            employee.setEmail(email);
            employee.setUser(user); // Assign User to Employee

            // Save Employee (User will be saved due to cascading)
            Employee createdEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.ok(createdEmployee);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody Map<String, Object> requestBody) {
        Long id = ((Number) requestBody.get("id")).longValue();
        String firstName = (String) requestBody.get("firstName");
        String lastName = (String) requestBody.get("lastName");
        String email = (String) requestBody.get("email");
        String role = (String) requestBody.get("role");

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid employee ID");
        }
//        if (firstName != null && firstName.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("First name cannot be empty");
//        }
//        if (lastName != null && lastName.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Last name cannot be empty");
//        }
//        if (email != null && email.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Email cannot be empty");
//        }

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setRole(role);

        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid employee ID");
        }
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PostMapping("/getById")
    public ResponseEntity<?> getEmployeeById(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid employee ID");
        }
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PatchMapping("/activate")
    public ResponseEntity<?> activateEmployee(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid employee ID");
        }
        return ResponseEntity.ok(employeeService.activateEmployee(id));
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<?> deactivateEmployee(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid employee ID");
        }
        return ResponseEntity.ok(employeeService.deactivateEmployee(id));
    }
}