package com.project.demo.rest.product;


import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductRestController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole( 'SUPER_ADMIN')")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);

    }



    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole( 'SUPER_ADMIN')")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setStock(product.getStock());
                    existingProduct.setCategory(product.getCategory());
                    return productRepository.save(existingProduct);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return productRepository.save(product);
                });
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }





}
