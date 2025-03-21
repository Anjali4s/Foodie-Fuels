package com.curiouscoders.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String name;
    private String email;
    private String password; // Include password if required
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

