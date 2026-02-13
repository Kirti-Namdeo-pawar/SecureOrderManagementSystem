package com.OrderService.OrderService.service;

import com.OrderService.OrderService.Entities.Order;
import com.OrderService.OrderService.Entities.OrderItem;
import com.OrderService.OrderService.Repository.OrderItemRepository;
import com.OrderService.OrderService.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AdminOrderServiceImpl implements AdminOrderService{

    private final OrderRepository orderRepo;
    private final OrderItemRepository itemRepo;

    public AdminOrderServiceImpl(OrderRepository orderRepo, OrderItemRepository itemRepo) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Order getOrderByOrderId(Long orderId) {
        return orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getOrdersByProductId(Long productId) {
        return itemRepo.findOrdersByProductId(productId);
    }

    @Override
    public List<Order> getAllOrdersplacedOnDate(LocalDate date) {
        return orderRepo.findOrdersPlacedOn(date);
    }

    @Override
    public long countOrdersByProductId(Long productId) {
        return itemRepo.countDistinctOrdersByProductId(productId);
    }

    @Override
    public long countOrdersByDate(LocalDate date) {
        return orderRepo.countOrdersPlacedOn(date);
    }
}
