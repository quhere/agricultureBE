package com.quang.tttn.repository;

import com.quang.tttn.model.entity.SellerWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerWarehouseRepository extends JpaRepository<SellerWarehouse, Long> {

}
