package com.quang.tttn.repository;

import com.quang.tttn.model.entity.Distributor;
import com.quang.tttn.model.entity.DistributorSeller;
import com.quang.tttn.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributorSellerRepository extends JpaRepository<DistributorSeller, Long> {
    DistributorSeller findByDistributorWarehouse_WarehouseId(Long warehouseId);
    DistributorSeller findBySellerWarehouse_WarehouseId(Long warehouseId);

    @Query("SELECT DISTINCT s FROM Seller s " +
            "JOIN s.distributorSellers ds " +
            "JOIN ds.distributorWarehouse dw " +
            "WHERE dw.distributor.distributorId = :distributorId")
    List<Seller> findSellersByDistributorId(@Param("distributorId") Long distributorId);

    @Query("SELECT ds FROM DistributorSeller ds " +
            "JOIN ds.distributorWarehouse dw " +
            "WHERE dw.distributor.distributorId = :distributorId")
    List<DistributorSeller> findAllByDistributorId(@Param("distributorId") Long distributorId);

    @Query("SELECT ds FROM DistributorSeller ds " +
            "WHERE ds.seller.sellerId = :sellerId")
    List<DistributorSeller> findAllBySellerId(@Param("sellerId") Long sellerId);

    @Query("SELECT ds.distributorWarehouse.distributor " +
            "FROM DistributorSeller ds " +
            "WHERE ds.seller.sellerId = :sellerId")
    List<Distributor> findDistributorsBySellerId(@Param("sellerId") Long sellerId);
}
