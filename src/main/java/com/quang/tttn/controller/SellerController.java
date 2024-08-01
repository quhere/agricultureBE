package com.quang.tttn.controller;

import com.quang.tttn.model.Response.SellerResponse;
import com.quang.tttn.model.entity.Seller;
import com.quang.tttn.model.mapper.SellerMapper;
import com.quang.tttn.model.request.SellerRequest;
import com.quang.tttn.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@CrossOrigin(origins = "*")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerMapper sellerMapper;

    @GetMapping
    public ResponseEntity<List<SellerResponse>> getAllSellers() {
        List<Seller> sellers = sellerService.findAllSeller();
        if (sellers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sellerMapper.toSellerResponses(sellers));
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerResponse> getSellerById(@PathVariable Long sellerId) {
        Seller seller = sellerService.findSellerById(sellerId);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sellerMapper.toSellerResponse(seller));
    }

    @PostMapping("/create")
    public ResponseEntity<SellerResponse> createSeller(@RequestBody SellerRequest request) {
        Seller seller = new Seller();
        seller.setName(request.getName());
        seller.setEmail(request.getEmail());
        seller.setPhoneNumber(request.getPhoneNumber());
        seller.setAddress(request.getAddress());
        seller.setFax(request.getFax());

        return ResponseEntity.ok(
                sellerMapper.toSellerResponse(
                        sellerService.createSeller(seller)
        ));
    }

}
