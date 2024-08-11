package com.quang.tttn.repository;

import com.quang.tttn.model.entity.ProductDistributor;
import com.quang.tttn.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDistributorRepository extends JpaRepository<ProductDistributor, Long> {
    @Query("SELECT pd FROM ProductDistributor pd WHERE pd.product.productId = :productId AND pd.distributor.distributorId = :distributorId AND pd.status = 'pending'")
    ProductDistributor findByProductIdAndDistributorIdAndStatusWaiting(
            @Param("productId") Long productId,
            @Param("distributorId") Long distributorId
    );

    ProductDistributor findByDistributorWarehouse_WarehouseId(Long warehouseId);

    @Query("SELECT pd FROM ProductDistributor pd JOIN pd.product p WHERE p.supplier.supplierId = :supplierId")
    List<ProductDistributor> findAllByProductSupplierId(@Param("supplierId") Long supplierId);

    @Query("SELECT pd FROM ProductDistributor pd " +
            "WHERE pd.distributor.distributorId = :distributorId")
    List<ProductDistributor> findAllByDistributorId(@Param("distributorId") Long distributorId);

    @Query("SELECT DISTINCT pd.product.supplier " +
            "FROM ProductDistributor pd " +
            "WHERE pd.distributor.distributorId = :distributorId")
    List<Supplier> findSuppliersByDistributorId(@Param("distributorId") Long distributorId);
}
