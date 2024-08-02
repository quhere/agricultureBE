package com.quang.tttn.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "distributor_sellers")
public class DistributorSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributor_warehouse_id", nullable = false)
    private DistributorWarehouse distributorWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(nullable = false)
    private Long quantity;

    @Column
    private LocalDateTime orderedDate;

    @Column(nullable = true)
    private LocalDateTime sentDate;

    @Column(nullable = true)
    private LocalDateTime receivedDate;

    @Column(nullable = true)
    private String status;

    @OneToOne
    @JoinColumn(name = "seller_warehouse_id", nullable = true)
    private SellerWarehouse sellerWarehouse;
}
