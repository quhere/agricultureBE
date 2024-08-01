package com.quang.tttn.service;

import com.quang.tttn.model.Response.SellerResponse;
import com.quang.tttn.model.entity.Seller;
import com.quang.tttn.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller findSellerById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    public List<Seller> findAllSeller() {
        return sellerRepository.findAll();
    }

    public Seller updateSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }

}
