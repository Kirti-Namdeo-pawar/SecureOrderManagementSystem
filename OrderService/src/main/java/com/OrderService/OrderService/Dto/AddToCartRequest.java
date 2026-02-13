package com.OrderService.OrderService.Dto;

import lombok.Data;

@Data
public class AddToCartRequest {

    private Long productId;
    private Integer quantity;
}
