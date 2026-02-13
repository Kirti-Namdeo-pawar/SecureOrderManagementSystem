package com.OrderService.OrderService.controller;

import com.OrderService.OrderService.Entities.Order;
import com.OrderService.OrderService.Entities.OrderItem;
import com.OrderService.OrderService.service.AdminOrderService;
import com.OrderService.OrderService.service.AdminOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final AdminOrderService adminService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(adminService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(adminService.getOrderByOrderId(orderId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> getOrdersByProductId(
            @PathVariable Long productId) {
        return ResponseEntity.ok(adminService.getOrdersByProductId(productId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Order>> getOrdersByDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return ResponseEntity.ok(
                adminService.getAllOrdersplacedOnDate(date)
        );
    }

    @GetMapping("/count/product/{productId}")
    public ResponseEntity<Long> countOrdersByProduct(
            @PathVariable Long productId) {
        return ResponseEntity.ok(
                adminService.countOrdersByProductId(productId)
        );
    }

    @GetMapping("/count/date/{date}")
    public ResponseEntity<Long> countOrdersByDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return ResponseEntity.ok(
                adminService.countOrdersByDate(date)
        );
    }
}
