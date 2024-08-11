package com.quang.tttn.service;

import com.quang.tttn.model.entity.Supplier;
import com.quang.tttn.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }

    public Supplier findByEmail(String email) {
        return supplierRepository.findByEmail(email);
    }
}
