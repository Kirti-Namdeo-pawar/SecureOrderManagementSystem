package com.ProductService.ProductService.Repository;

import com.ProductService.ProductService.Entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    List<Products> findByCategory(String category);
    List<Products> findByName(String name);
    Optional<Products> findById(Long prodid);
}
