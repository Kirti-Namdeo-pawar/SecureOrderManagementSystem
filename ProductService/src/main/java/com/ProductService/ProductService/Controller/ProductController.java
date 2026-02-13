package com.ProductService.ProductService.Controller;

import com.ProductService.ProductService.Entities.Products;
import com.ProductService.ProductService.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ===============================
    // USER + ADMIN
    // ===============================

    // View all products
    @GetMapping
    public List<Products> getAllProducts() {
        return service.getAllProducts();
    }

    // Search by product name
    @GetMapping("/name/{name}")
    public List<Products> getProductByName(@PathVariable String name) {
        return service.getProductByName(name);
    }

    // Filter by category
    @GetMapping("/category/{category}")
    public List<Products> getProductsByCategory(@PathVariable String category) {
        return service.getProductsByCategory(category);
    }

    @GetMapping("/{id}")
    public Products getProductById(@PathVariable Long id) {
        return service.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }


    // ADMIN ONLY
    // Add product
    @PostMapping
    public Products addProduct(
            @RequestHeader("X-User-Role") String role,
            @RequestBody Products product) {

        checkAdmin(role);
        return service.addProduct(product);
    }

    // Update product
    @PutMapping("/{id}")
    public Products updateProduct(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id,
            @RequestBody Products product) {

        checkAdmin(role);
        return service.updateProductDetails(id, product);
    }

    // Delete product
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id) {

        checkAdmin(role);
        service.deleteProduct(id);
    }
    //helper function
    private void checkAdmin(String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Access denied: ADMIN role required"
            );
        }
    }
}
