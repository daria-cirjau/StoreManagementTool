package com.store.category.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private UUID parentId;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private Boolean isActive;
    @Column(nullable = false, updatable = false)
    private Date createdAt;
    @Column(nullable = false)
    private Date updatedAt;

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
