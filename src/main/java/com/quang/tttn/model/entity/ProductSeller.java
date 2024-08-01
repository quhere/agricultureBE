package com.quang.tttn.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "product_sellers")
public class ProductSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(nullable = true)
    private LocalDateTime sentDate;

    @Column(nullable = true)
    private LocalDateTime receivedDate;

    @Column
    private Long quantity;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "warehouse_id", nullable = true)
    private SellerWarehouse sellerWarehouse;
}
