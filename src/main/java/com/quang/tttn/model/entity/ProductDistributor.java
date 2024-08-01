package com.quang.tttn.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Product product;

    @ManyToOne
    @JoinColumn(name = "distributor_id", nullable = false)
    private Distributor distributor;

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
    private DistributorWarehouse distributorWarehouse;
}
