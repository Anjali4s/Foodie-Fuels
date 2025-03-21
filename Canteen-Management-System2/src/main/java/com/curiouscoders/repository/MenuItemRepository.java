package com.curiouscoders.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curiouscoders.model.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByName(String name);
    List<MenuItem> findByCreatedBy_Id(Long id);
	//Optional<Employee> findByItemName(String itemName);
}

