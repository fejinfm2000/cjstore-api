package com.cjstore.api.repository;

import com.cjstore.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByStoreIdAndActiveTrue(UUID storeId);

    List<Product> findByStoreId(UUID storeId);
}
