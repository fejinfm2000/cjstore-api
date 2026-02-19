package com.cjstore.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String whatsapp;

    private String logo;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String theme; // light, dark

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private Long visits = 0L;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
