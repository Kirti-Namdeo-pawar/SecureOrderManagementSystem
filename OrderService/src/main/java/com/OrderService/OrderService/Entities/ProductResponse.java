package com.OrderService.OrderService.Entities;

import lombok.Data;

@Data
public class ProductResponse {
    private Long productId;
    private Double price;
    private String Category;
    private String productName;
    private Integer quantity;
}
