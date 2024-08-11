package com.quang.tttn.repository;

import com.quang.tttn.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);
    Supplier findByEmail(String email);
}
