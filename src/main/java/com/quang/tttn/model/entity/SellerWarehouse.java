package com.quang.tttn.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "seller_warehouses")
public class SellerWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    @ToString.Exclude
    private Seller seller;

    @OneToOne(mappedBy = "sellerWarehouse", cascade = CascadeType.ALL)
    @ToString.Exclude
    private ProductSeller productSeller;

    @OneToOne(mappedBy = "sellerWarehouse", cascade = CascadeType.ALL)
    @ToString.Exclude
    private DistributorSeller distributorSeller;
}
