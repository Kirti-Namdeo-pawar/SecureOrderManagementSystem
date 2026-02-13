package com.OrderService.OrderService.Repository;

import com.OrderService.OrderService.Entities.Order;
import com.OrderService.OrderService.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByProductId(Long productId);

    @Query("""
        SELECT DISTINCT oi.order
        FROM OrderItem oi
        WHERE oi.productId = :productId
    """)
    List<Order> findOrdersByProductId(Long productId);

    @Query("""
        SELECT COUNT(DISTINCT oi.order.orderId)
        FROM OrderItem oi
        WHERE oi.productId = :productId
    """)
    long countDistinctOrdersByProductId(Long productId);

}
