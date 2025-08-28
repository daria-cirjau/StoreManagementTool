package com.storemanagementtool.product.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String currency;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false, updatable = false)
    private Date createdAt;
    @Column(nullable = false)
    private Date updatedAt;
    @Column
    private String category;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = new Date();
    }


}
