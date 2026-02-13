package com.OrderService.OrderService.Repository;

import com.OrderService.OrderService.Entities.Order;
import com.OrderService.OrderService.Entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserId(Long userId);

    Optional<Order> findByOrderId(Long orderId);
    @Query("""
    SELECT o
    FROM Order o
    WHERE DATE(o.orderDate) = :date
""")
    List<Order> findOrdersPlacedOn(LocalDate date);

    @Query("""
    SELECT COUNT(o)
    FROM Order o
    WHERE DATE(o.orderDate) = :date
""")
    long countOrdersPlacedOn(LocalDate date);

    Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus status);


}
