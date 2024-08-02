package com.quang.tttn.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product_distributors")
public class ProductDistributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "distributor_id", nullable = false)
    @ToString.Exclude
    private Distributor distributor;

    @Column
    private LocalDateTime orderedDate;

    @Column(nullable = true)
    private LocalDateTime sentDate;

    @Column(nullable = true)
    private LocalDateTime receivedDate;

    @Column
    private Long quantity;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "distributor_warehouse_id", nullable = true)
    @ToString.Exclude
    private DistributorWarehouse distributorWarehouse;
}
