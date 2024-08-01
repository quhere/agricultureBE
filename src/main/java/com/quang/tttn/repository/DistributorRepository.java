package com.quang.tttn.repository;

import com.quang.tttn.model.entity.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {
    Distributor findByName(String name);
}
