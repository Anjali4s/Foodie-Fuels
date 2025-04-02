package com.curiouscoders.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curiouscoders.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByRole(String role); // Find employees by role
    List<Employee> findByActive(boolean active); // Find active or inactive employees
    boolean existsByEmail(String email);
    Employee findByEmail(String email);
    Optional<Employee> findFirstByOrderByIdAsc();
}

