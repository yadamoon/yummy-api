package com.bytepulse.yummy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "cakes")
@Data // Lombok generates getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor // Lombok generates a no-argument constructor
@AllArgsConstructor // Lombok generates an all-arguments constructor
public class Cake {

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
}
