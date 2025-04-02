package com.curiouscoders.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.curiouscoders.exception.EmployeeNotFoundException;
import com.curiouscoders.model.Employee;
import com.curiouscoders.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

	private final EmployeeRepository employeeRepository;
	//private final UserRepository userRepository;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    // Create a new employee
	@Override
	@Transactional
	public Employee createEmployee(Employee employee) {
	    if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
	        throw new IllegalArgumentException("First name cannot be empty");
	    }
	    if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
	        throw new IllegalArgumentException("Last name cannot be empty");
	    }
	    if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
	        throw new IllegalArgumentException("Email cannot be empty");
	    }
	    if (employeeRepository.existsByEmail(employee.getEmail())) {
	        throw new IllegalArgumentException("Email is already registered");
	    }
	    if (employee.getRole() == null || employee.getRole().trim().isEmpty()) {
	        employee.setRole("USER");  
	    }

	    
	 // Encode password before saving
	    if (employee.getPassword() != null && !employee.getPassword().trim().isEmpty()) {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
	    } else {
	        throw new IllegalArgumentException("Password cannot be empty");
	    }

	    // Ensure User is saved first before Employee
//	    if (employee.getUser() != null) {
//	        User user = employee.getUser();
//	        user.setActive(true); // Default active status
//	        user = userRepository.save(user); // Save User first
//	        employee.setUser(user); // Assign persisted user
//	    }

	    // Save Employee after ensuring User is persisted
	    employee.setActive(true); // Default status
	    return employeeRepository.save(employee);
	}

	
	
	
	
	
	
	
	
//    @Override
//    @Transactional
//    public Employee createEmployee(Employee employee) {
//        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
//            throw new IllegalArgumentException("First name cannot be empty");
//        }
//        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
//            throw new IllegalArgumentException("Last name cannot be empty");
//        }
//        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
//            throw new IllegalArgumentException("Email cannot be empty");
//        }
//        if (employeeRepository.existsByEmail(employee.getEmail())) {
//            throw new IllegalArgumentException("Email is already registered");
//        }
//
//        employee.setActive(true); // Default status
//        return employeeRepository.save(employee);
//    }

    // Update an existing employee
    @Override
    @Transactional
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        existingEmployee.setFirstName(
                updatedEmployee.getFirstName() != null ? updatedEmployee.getFirstName() : existingEmployee.getFirstName());

        existingEmployee.setLastName(
                updatedEmployee.getLastName() != null ? updatedEmployee.getLastName() : existingEmployee.getLastName());

        existingEmployee.setEmail(
                updatedEmployee.getEmail() != null ? updatedEmployee.getEmail() : existingEmployee.getEmail());

        existingEmployee.setRole(
                updatedEmployee.getRole() != null ? updatedEmployee.getRole() : existingEmployee.getRole());

        existingEmployee.setActive(
                updatedEmployee.getActive() != null ? updatedEmployee.getActive() : existingEmployee.getActive());

        return employeeRepository.save(existingEmployee);
    }

    // Delete an employee
    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    // Get employee by ID
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    // Get all employees
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Activate an employee
    @Override
    @Transactional
    public Employee activateEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employee.setActive(true);
        return employeeRepository.save(employee);
    }

    // Deactivate an employee
    @Override
    @Transactional
    public Employee deactivateEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employee.setActive(false);
        return employeeRepository.save(employee);
    }
}