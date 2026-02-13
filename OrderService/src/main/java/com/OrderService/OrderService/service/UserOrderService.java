package com.OrderService.OrderService.service;

import com.OrderService.OrderService.Entities.Order;

import java.util.List;

public interface UserOrderService {
    Order addProductTCart(Long userId,Long productId,int quantity);

    Order updateProductQuantity(Long userId,Long orderId,Long productId,int quantity);
    Order removeProductFromCart(Long userId,Long orderId,Long prodcutId);
    Order placeOrder(Long userId,Long orderId);
    List<Order> viewMyOrders(Long userId);



}
