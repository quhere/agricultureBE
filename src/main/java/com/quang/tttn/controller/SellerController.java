package com.quang.tttn.controller;

import com.quang.tttn.model.Response.DisToSellResponse;
import com.quang.tttn.model.Response.SellerResponse;
import com.quang.tttn.model.entity.DistributorSeller;
import com.quang.tttn.model.entity.DistributorWarehouse;
import com.quang.tttn.model.entity.Seller;
import com.quang.tttn.model.entity.SellerWarehouse;
import com.quang.tttn.model.mapper.DisToSellMapper;
import com.quang.tttn.model.mapper.SellerMapper;
import com.quang.tttn.model.request.DisToSellRequest;
import com.quang.tttn.model.request.SellerRequest;
import com.quang.tttn.service.DistributorSellerService;
import com.quang.tttn.service.DistributorWarehouseService;
import com.quang.tttn.service.SellerService;
import com.quang.tttn.service.SellerWareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @Autowired
    private DistributorWarehouseService distributorWarehouseService;
    @Autowired
    private DistributorSellerService distributorSellerService;
    @Autowired
    private DisToSellMapper disToSellMapper;
    @Autowired
    private SellerWareHouseService sellerWareHouseService;

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

    @PostMapping("/order")
    public ResponseEntity<DisToSellResponse> order(
            @RequestBody DisToSellRequest request
    ) {
        DistributorSeller distributorSeller = new DistributorSeller();

        DistributorWarehouse distributorWarehouse = distributorWarehouseService
                .getDistributorWarehouseById(
                        request.getWarehouseId()
                );
        Seller seller = sellerService.findSellerById(request.getSellerId());
        if (distributorWarehouse == null || seller == null) {
            return ResponseEntity.notFound().build();
        }

        if (distributorWarehouse.getQuantity()
                < request.getQuantity()
        ) {
            return ResponseEntity.badRequest().build();
        }

        distributorSeller.setDistributorWarehouse(distributorWarehouse);
        distributorSeller.setSeller(seller);
        distributorSeller.setQuantity(request.getQuantity());
        distributorSeller.setOrderedDate(LocalDateTime.now());
        distributorSeller.setStatus("waiting");

        DistributorSeller savedTransaction = distributorSellerService.save(distributorSeller);

        return ResponseEntity.ok(
                disToSellMapper.toResponse(savedTransaction)
        );
    }

    @PostMapping("/received/{transactionId}")
    public ResponseEntity<DisToSellResponse> receive(@PathVariable Long transactionId) {
        DistributorSeller distributorSeller = distributorSellerService.findDistributorSellerById(transactionId);
        if (distributorSeller == null) {
            return ResponseEntity.notFound().build();
        }
        distributorSeller.setReceivedDate(LocalDateTime.now());
        distributorSeller.setStatus("received");
        DistributorSeller savedTransaction = distributorSellerService.save(distributorSeller);

        SellerWarehouse sellerWarehouse = distributorSeller.getSellerWarehouse();
        sellerWarehouse.setQuantity(distributorSeller.getQuantity());
        sellerWareHouseService.save(sellerWarehouse);

        return ResponseEntity.ok(
                disToSellMapper.toResponse(savedTransaction)
        );
    }
}
