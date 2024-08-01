package com.quang.tttn.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "distributor_warehouses")
public class DistributorWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    @Column(nullable = false)
    private Long quantity; // current quantity in warehouse

    @ManyToOne
    @JoinColumn(name = "distributor_id", nullable = false)
    @ToString.Exclude
    private Distributor distributor;

    @OneToOne(mappedBy = "distributorWarehouse", cascade = CascadeType.ALL)
    private ProductDistributor productDistributor;

    @OneToMany(mappedBy = "distributorWarehouse", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<DistributorSeller> distributorSellers = new ArrayList<>();
}
