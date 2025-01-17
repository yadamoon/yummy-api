package com.bytepulse.yummy.dto.cakes;

import jakarta.validation.constraints.*;

public class CreateCakeDto {

    @NotBlank(message = "Cake name is required.")
    @Size(max = 100, message = "Cake name must not exceed 100 characters.")
    private String name;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than zero.")
    private Double price;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
