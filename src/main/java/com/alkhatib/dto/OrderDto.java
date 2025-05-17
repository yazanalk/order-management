package com.alkhatib.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderDto {
    private Long id;
    
    @NotBlank(message = "Description must not be blank")
    private String description;
    
    
    private Long userId;

    // Constructors
    public OrderDto() {}

    public OrderDto(Long id, String description, Long userId) {
        this.id = id;
        this.description = description;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}