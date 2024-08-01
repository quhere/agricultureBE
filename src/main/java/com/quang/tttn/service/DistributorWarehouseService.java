package com.quang.tttn.service;

import com.quang.tttn.model.entity.DistributorWarehouse;
import com.quang.tttn.repository.DistributorWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributorWarehouseService {
    @Autowired
    private DistributorWarehouseRepository distributorWarehouseRepository;

    public List<DistributorWarehouse> getAllDistributorWarehouse() {
        return distributorWarehouseRepository.findAll();
    }

    public DistributorWarehouse getDistributorWarehouseById(Long id) {
        return distributorWarehouseRepository.findById(id).orElse(null);
    }

    public DistributorWarehouse save(DistributorWarehouse distributorWarehouse) {
        return distributorWarehouseRepository.save(distributorWarehouse);
    }

    public DistributorWarehouse update(DistributorWarehouse distributorWarehouse) {
        return distributorWarehouseRepository.save(distributorWarehouse);
    }

    public void delete(DistributorWarehouse distributorWarehouse) {
        distributorWarehouseRepository.delete(distributorWarehouse);
    }
}
