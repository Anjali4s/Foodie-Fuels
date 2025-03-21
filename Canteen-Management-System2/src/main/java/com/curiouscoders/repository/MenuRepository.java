package com.curiouscoders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.curiouscoders.model.MenuItem;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategory(String category); // Find menu items by category
    List<MenuItem> findByAvailable(boolean available); // Find available or unavailable menu items
    List<MenuItem> findByCreatedBy_Id(Long createdById); // Find menu items created by a specific employee
}

