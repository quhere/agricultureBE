package com.quang.tttn.repository;

import com.quang.tttn.model.entity.DistributorSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorSellerRepository extends JpaRepository<DistributorSeller, Long> {
}
