package com.cjstore.api.controller;

import com.cjstore.api.entity.Store;
import com.cjstore.api.repository.StoreRepository;
import com.cjstore.api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public StoreController(StoreRepository storeRepository, UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Store> getStoreBySlug(@PathVariable String slug) {
        return storeRepository.findBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody Store store, @RequestParam UUID ownerId) {
        if (storeRepository.existsBySlug(store.getSlug())) {
            return ResponseEntity.badRequest().body("Error: Slug is already taken!");
        }

        return userRepository.findById(ownerId).map(owner -> {
            store.setOwner(owner);
            Store savedStore = storeRepository.save(store);
            return ResponseEntity.ok(savedStore);
        }).orElse(ResponseEntity.badRequest().body("Error: Owner not found!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStore(@PathVariable UUID id, @RequestBody Store storeDetails) {
        return storeRepository.findById(id).map(store -> {
            store.setName(storeDetails.getName());
            store.setWhatsapp(storeDetails.getWhatsapp());
            store.setLogo(storeDetails.getLogo());
            store.setDescription(storeDetails.getDescription());
            store.setTheme(storeDetails.getTheme());
            return ResponseEntity.ok(storeRepository.save(store));
        }).orElse(ResponseEntity.notFound().build());
    }
}
