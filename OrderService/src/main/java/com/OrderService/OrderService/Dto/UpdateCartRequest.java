package com.OrderService.OrderService.Dto;

import lombok.Data;

@Data
public class UpdateCartRequest {
    private Long orderId;
    private Long productId;
    private int quantity;
}
