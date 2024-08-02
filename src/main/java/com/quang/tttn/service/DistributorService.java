package com.quang.tttn.service;

import com.quang.tttn.model.entity.Distributor;
import com.quang.tttn.repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributorService {
    @Autowired
    private DistributorRepository distributorRepository;

    public List<Distributor> getAllDistributor() {
        return distributorRepository.findAll();
    }

    public Distributor getDistributorById(Long id) {
        return distributorRepository.findById(id).orElse(null);
    }

    public Distributor saveDistributor(Distributor distributor) {
        return distributorRepository.save(distributor);
    }

    public void deleteDistributor(Long id) {
        distributorRepository.deleteById(id);
    }

    public List<Distributor> findAllDistributorInProductDistributors() {
        return distributorRepository.findAllDistributorsWithProductDistributors();
    }
}
