package com.quang.tttn.service;

import com.quang.tttn.model.entity.DistributorSeller;
import com.quang.tttn.repository.DistributorSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributorSellerService {
    @Autowired
    private DistributorSellerRepository distributorSellerRepository;

    public DistributorSeller findDistributorSellerById(Long id) {
        return distributorSellerRepository.findById(id).orElse(null);
    }

    public List<DistributorSeller> findDistributorSellerByDistributorId(Long id) {
        return distributorSellerRepository.findAll();
    }

    public DistributorSeller save(DistributorSeller distributorSeller) {
        return distributorSellerRepository.save(distributorSeller);
    }

    public DistributorSeller update(DistributorSeller distributorSeller) {
        return distributorSellerRepository.save(distributorSeller);
    }

}
