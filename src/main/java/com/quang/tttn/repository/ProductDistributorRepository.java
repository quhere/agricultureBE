package com.quang.tttn.repository;

import com.quang.tttn.model.entity.ProductDistributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductDistributorRepository extends JpaRepository<ProductDistributor, Long> {
    @Query("SELECT pd FROM ProductDistributor pd WHERE pd.product.productId = :productId AND pd.distributor.distributorId = :distributorId AND pd.status = 'Waiting'")
    ProductDistributor findByProductIdAndDistributorIdAndStatusWaiting(
            @Param("productId") Long productId,
            @Param("distributorId") Long distributorId
    );
}
