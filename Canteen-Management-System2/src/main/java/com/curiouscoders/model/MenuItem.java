package com.curiouscoders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private Double price;
    private Boolean available;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false,referencedColumnName = "id")
    @JsonIgnore
    private Employee createdBy; // Employee who created the menu item

    public MenuItem(String name, String category, Double price, Boolean available, String imageUrl, Employee createdBy) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
    }
}