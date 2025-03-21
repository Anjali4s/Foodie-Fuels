package com.curiouscoders.service;

import java.util.List;

import com.curiouscoders.model.Employee;

public interface IEmployeeService {
	Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    Employee activateEmployee(Long id);
    Employee deactivateEmployee(Long id);
}
