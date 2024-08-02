package com.quang.tttn.repository;

import com.quang.tttn.model.entity.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {
    Distributor findByName(String name);
    @Query("SELECT DISTINCT d FROM Distributor d JOIN d.productDistributors pd")
    List<Distributor> findAllDistributorsWithProductDistributors();
}
