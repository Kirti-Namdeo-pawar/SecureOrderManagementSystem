package com.OrderService.OrderService.service;

import com.OrderService.OrderService.Entities.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="ProductService",
url = "http://localhost:8082")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id);

@PutMapping("/products/{id}/reduce")
    void reduceProductQuantity(@PathVariable Long id, @RequestParam int quantity); //reduce product quantity after order

}
