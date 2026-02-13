package com.OrderService.OrderService.service;

import com.OrderService.OrderService.Entities.Order;
import com.OrderService.OrderService.Entities.OrderItem;
import com.OrderService.OrderService.Entities.OrderStatus;
import com.OrderService.OrderService.Entities.ProductResponse;
import com.OrderService.OrderService.Repository.OrderItemRepository;
import com.OrderService.OrderService.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
public class UserOrderServiceImpl implements UserOrderService{


    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ProductClient prodClient;

    public UserOrderServiceImpl(OrderRepository orderRepo, OrderItemRepository orderItemRepo, ProductClient prodClient) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.prodClient = prodClient;
    }


    @Override
    public Order addProductTCart(Long userId, Long productId, int quantity) {
        Order order=getOrCreateCart(userId);
       ProductResponse product=prodClient.getProductById(productId);
     if(product.getQuantity() <quantity){
         throw new RuntimeException("Insufficient stock available");
     }
        OrderItem item=order.getOrderItems().stream().filter(i->i.getProductId().equals(productId)).findFirst().orElse(null);
        if(item==null){
            item=new OrderItem();
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setOrder(order);
            item.setPricePerUnit(product.getPrice());
            order.getOrderItems().add(item);
        }else{
            item.setQuantity(item.getQuantity()+quantity);
        };
        recalculateTotal(order);
        return orderRepo.save(order);
    }

    @Override
    public Order updateProductQuantity(Long userId, Long orderId, Long productId, int quantity) {
        Order order = getOrCreateCart(userId);
        OrderItem item = order.getOrderItems()
                .stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
        if (quantity <= 0) {
            order.getOrderItems().remove(item);
            orderItemRepo.delete(item);
        } else {
            item.setQuantity(quantity);

        }
        recalculateTotal(order);
        return orderRepo.save(order);
    }

    @Override
    public Order removeProductFromCart(Long userId, Long orderId, Long prodcutId) {
        return null;
    }

    @Override
    public Order placeOrder(Long userId, Long orderId) {
        Order order=getOrCreateCart(userId);
        if(order.getOrderItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(LocalDateTime.now());
        return orderRepo.save(order);
    }

    @Override
    public List<Order> viewMyOrders(Long userId) {
        return orderRepo.findByUserId(userId);
    }



    private void recalculateTotal(Order order) {
        double total = order.getOrderItems()
                .stream()
                .mapToDouble(i -> i.getPricePerUnit() * i.getQuantity())
                .sum();
        order.setTotalAmt(total);
    }


    private Order getOrCreateCart(Long userId){
        return orderRepo.findByUserIdAndStatus(userId, OrderStatus.CREATED)
                .orElseGet(()->{
                 Order order=new Order();
                 order.setUserId(userId);
                 order.setStatus(OrderStatus.CREATED);
                 order.setOrderDate(LocalDateTime.now());
                 order.setTotalAmt(0.0);
                return orderRepo.save(order);
        });
    }
}

