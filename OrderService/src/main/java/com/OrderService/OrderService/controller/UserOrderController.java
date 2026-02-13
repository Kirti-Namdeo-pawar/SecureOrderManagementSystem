package com.OrderService.OrderService.controller;

import com.OrderService.OrderService.Dto.AddToCartRequest;
import com.OrderService.OrderService.Dto.PlaceOrderRequest;
import com.OrderService.OrderService.Dto.UpdateCartRequest;
import com.OrderService.OrderService.Entities.Order;
import com.OrderService.OrderService.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userService;

    @PostMapping("/cart/add")
    public ResponseEntity<Order> addToCart(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody AddToCartRequest request) {

        Order order = userService.addProductTCart(
                userId,
                request.getProductId(),
                request.getQuantity()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/cart/update")
    public ResponseEntity<Order> updateQuantity(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody UpdateCartRequest request) {

        Order order = userService.updateProductQuantity(
                userId,
                request.getOrderId(),
                request.getProductId(),
                request.getQuantity()
        );

        return ResponseEntity.ok(order);
    }

@DeleteMapping("/cart/remove")
    public ResponseEntity<Order> removeProductfromCart(@RequestHeader("X-USER-ID") Long userId,@RequestParam Long orderId,@RequestParam Long productId) {
    Order order = userService.removeProductFromCart(userId,orderId,productId);
    return ResponseEntity.ok(order);

}

@PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestHeader("X-USER-ID") Long userId, @RequestBody PlaceOrderRequest req){
        Order order=userService.placeOrder(userId,req.getOrderId());
        return ResponseEntity.ok(order);
}
@GetMapping("/my")
    public ResponseEntity<List<Order>> viewMyOrders(@RequestHeader("X-USER-ID") Long userId){
        return ResponseEntity.ok(userService.viewMyOrders(userId));
}
}
