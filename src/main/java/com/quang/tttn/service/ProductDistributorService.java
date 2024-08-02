package com.quang.tttn.service;

import com.quang.tttn.model.entity.ProductDistributor;
import com.quang.tttn.repository.ProductDistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDistributorService {
    @Autowired
    private ProductDistributorRepository productDistributorRepository;

    public List<ProductDistributor> findAll() {
        return productDistributorRepository.findAll();
    }

    public ProductDistributor findById(Long id) {
        return productDistributorRepository.findById(id).orElse(null);
    }

    public ProductDistributor save(ProductDistributor productDistributor) {
        return productDistributorRepository.save(productDistributor);
    }

    public ProductDistributor update(ProductDistributor productDistributor) {
        return productDistributorRepository.save(productDistributor);
    }

    public ProductDistributor findByProductIdAndDistributorIdAndStatusWaiting(Long productId, Long distributorId) {
        return productDistributorRepository.findByProductIdAndDistributorIdAndStatusWaiting(
                productId,
                distributorId
        );
    }

    public ProductDistributor findByDistributorWarehouseId(Long distributorWarehouseId) {
        return productDistributorRepository.findByDistributorWarehouse_WarehouseId(distributorWarehouseId);
    }

    public List<ProductDistributor> findBySupplierId(Long supplierId) {
        return productDistributorRepository
                .findAllByProductSupplierId(supplierId);
    }
}
