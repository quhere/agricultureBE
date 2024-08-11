package com.quang.tttn.repository;

import com.quang.tttn.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByName(String name);
    Seller findByEmail(String email);
}
