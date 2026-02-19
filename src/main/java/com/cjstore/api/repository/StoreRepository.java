package com.cjstore.api.repository;

import com.cjstore.api.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findBySlug(String slug);
    Boolean existsBySlug(String slug);
}
