package com.ProductService.ProductService.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Product_details")
@Data
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private double price;


}
