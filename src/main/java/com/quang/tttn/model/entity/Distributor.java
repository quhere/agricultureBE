package com.quang.tttn.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "distributors")
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long distributorId;

    @Column
    private String role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column
    private String fax;

    @Column
    private Boolean status;

    @Column
    private String avtUrl;

    @Column
    private String taxCode;

    @Column
    private String establishment;

    @Column
    private String manager;

    @Column
    private String activated;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<DistributorWarehouse> warehouses = new ArrayList<>();

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductDistributor> productDistributors = new ArrayList<>();
}
