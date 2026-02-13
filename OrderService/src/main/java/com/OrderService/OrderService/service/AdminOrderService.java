package com.OrderService.OrderService.service;

import com.OrderService.OrderService.Entities.Order;

import java.time.LocalDate;
import java.util.List;

public interface AdminOrderService {
    List<Order> getAllOrders();
    Order getOrderByOrderId(Long orderId);
   List<Order> getOrdersByProductId(Long productId);
    List<Order> getAllOrdersplacedOnDate(LocalDate date);
    long countOrdersByProductId(Long productId);
    long countOrdersByDate(LocalDate date);
}
