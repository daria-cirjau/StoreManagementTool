package com.store.price.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "priceHistory")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistory {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private UUID productId;
    @Column(nullable = false)
    private Long oldPrice;
    @Column(nullable = false)
    private Long newPrice;
    @Column(nullable = false)
    private String changedBy;
    @Column
    private String reason;
    @Column
    private Date changedAt;

    @PrePersist
    public void onCreate() {
        changedAt = new Date();
    }
}
