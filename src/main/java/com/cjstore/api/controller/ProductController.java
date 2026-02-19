package com.cjstore.api.controller;

import com.cjstore.api.entity.Product;
import com.cjstore.api.repository.ProductRepository;
import com.cjstore.api.repository.StoreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public ProductController(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @GetMapping("/store/{storeId}")
    public List<Product> getProductsByStore(@PathVariable UUID storeId) {
        return productRepository.findByStoreId(storeId);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product, @RequestParam UUID storeId) {
        return storeRepository.findById(storeId).map(store -> {
            product.setStore(store);
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.badRequest().body("Error: Store not found!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            product.setStock(productDetails.getStock());
            product.setImage(productDetails.getImage());
            product.setActive(productDetails.getActive());
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
