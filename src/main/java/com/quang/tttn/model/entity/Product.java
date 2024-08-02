package com.quang.tttn.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column
    private Long quantity;

    @Column
    private String characteristic;

    @Column
    private String seed;

    @Column
    private String cook;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column
    private String image;

    @Column
    private LocalDateTime plantingDate;

    @Column
    private LocalDateTime harvestDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    @ToString.Exclude
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductDistributor> productDistributors = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductSeller> productSellers = new ArrayList<>();
}
