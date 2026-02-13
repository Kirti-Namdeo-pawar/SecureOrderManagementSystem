package com.ProductService.ProductService.Service;

import com.ProductService.ProductService.Entities.Products;
import com.ProductService.ProductService.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository prodRepo;

    public ProductService(ProductRepository prodRepo) {
        this.prodRepo = prodRepo;
    }


    public Products addProduct(Products product){
        return prodRepo.save(product);
    }

    public List<Products> getAllProducts(){
        return prodRepo.findAll();

    }
    public Optional<Products> getProductById(Long prodId){
        return prodRepo.findById(prodId);
    }
    public  List<Products> getProductsByCategory(String category){
        return prodRepo.findByCategory(category);

    }
    public List<Products> getProductByName(String name){
        return prodRepo.findByName(name);
    }
    public void deleteProduct(Long id){
        prodRepo.deleteById(id);
    }
    public Products updateProductDetails(Long id,Products updatedProduct){
        Products product = prodRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());

        return prodRepo.save(product);

    }
    public void reduceQuantity(Long productId,int quantity){
        Products product=prodRepo.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));

        if(product.getQuantity()<quantity){
            throw new RuntimeException("Insufficient Stock available");

        }
        product.setQuantity(product.getQuantity()-quantity);
        prodRepo.save(product);
    }
}
