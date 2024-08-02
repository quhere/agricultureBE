package com.quang.tttn.service;

import com.quang.tttn.model.entity.SellerWarehouse;
import com.quang.tttn.repository.SellerWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerWareHouseService {
    @Autowired
    private SellerWarehouseRepository sellerWarehouseRepository;

    public SellerWarehouse getSellerWarehouseById(Long id) {
        return sellerWarehouseRepository.findById(id).orElse(null);
    }

    public List<SellerWarehouse> getAllSellerWarehouse() {
        return sellerWarehouseRepository.findAll();
    }

    public SellerWarehouse save(SellerWarehouse sellerWarehouse) {
        return sellerWarehouseRepository.save(sellerWarehouse);
    }

    public void delete(SellerWarehouse sellerWarehouse) {
        sellerWarehouseRepository.delete(sellerWarehouse);
    }
}
