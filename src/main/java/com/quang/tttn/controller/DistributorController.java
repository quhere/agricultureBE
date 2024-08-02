package com.quang.tttn.controller;

import com.quang.tttn.model.Response.DisToSellResponse;
import com.quang.tttn.model.Response.DistributorResponse;
import com.quang.tttn.model.Response.SupToDisResponse;
import com.quang.tttn.model.entity.*;
import com.quang.tttn.model.mapper.DisToSellMapper;
import com.quang.tttn.model.mapper.DistributorMapper;
import com.quang.tttn.model.mapper.SupToDisMapper;
import com.quang.tttn.model.request.DisToSellRequest;
import com.quang.tttn.model.request.DistributorRequest;
import com.quang.tttn.model.request.SupToDisRequest;
import com.quang.tttn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/distributors")
@CrossOrigin(origins = "*")
public class DistributorController {
    @Autowired
    private DistributorService distributorService;
    @Autowired
    private DistributorMapper distributorMapper;
    @Autowired
    private ProductDistributorService productDistributorService;
    @Autowired
    private SupToDisMapper supToDisMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private DistributorWarehouseService distributorWarehouseService;
    @Autowired
    private DistributorSellerService distributorSellerService;
    @Autowired
    private DisToSellMapper disToSellMapper;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerWareHouseService sellerWareHouseService;

    @GetMapping
    public ResponseEntity<List<DistributorResponse>> getAllDistributors() {
        List<Distributor> distributors = distributorService.getAllDistributor();
        List<DistributorResponse> distributorResponses = new ArrayList<>();
        if (distributors != null && !distributors.isEmpty()) {
            distributorResponses = distributorMapper.toDistributorResponses(distributors);
            return ResponseEntity.ok(distributorResponses);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{distributorId}")
    public ResponseEntity<DistributorResponse> getDistributorById(@PathVariable Long distributorId) {
        Distributor distributor = distributorService.getDistributorById(distributorId);
        DistributorResponse distributorResponse = new DistributorResponse();
        if (distributor != null) {
            distributorResponse = distributorMapper.toDistributorResponse(distributor);
            return ResponseEntity.ok(distributorResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<DistributorResponse> createDistributor(@RequestBody DistributorRequest request) {
        Distributor distributor = new Distributor();
        distributor.setName(request.getName());
        distributor.setEmail(request.getEmail());
        distributor.setPhoneNumber(request.getPhoneNumber());
        distributor.setAddress(request.getAddress());
        distributor.setFax(request.getFax());

        return ResponseEntity.ok(
                distributorMapper.toDistributorResponse(
                        distributorService.saveDistributor(distributor)
                )
        );
    }

    @PostMapping("/received/{transactionId}")
    public ResponseEntity<SupToDisResponse> receivedDistributor(
            @PathVariable Long transactionId
    ) {
        ProductDistributor productDistributor = productDistributorService.findById(transactionId);

        if (productDistributor != null) {
            // update warehouse
            DistributorWarehouse distributorWarehouse = productDistributor.getDistributorWarehouse();
            if (distributorWarehouse != null) {
                distributorWarehouse.setQuantity(productDistributor.getQuantity());
                distributorWarehouseService.update(distributorWarehouse);
            }

            productDistributor.setReceivedDate(LocalDateTime.now());
            productDistributor.setStatus("received");
            productDistributor = productDistributorService.update(productDistributor);
            return ResponseEntity.ok(supToDisMapper.toSupToDisResponse(productDistributor));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/order")
    public ResponseEntity<SupToDisResponse> orderDistributor(
            @RequestBody SupToDisRequest request
    ) {
        // check quantity
        Product product = productService.findById(request.getProductId());
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        if (product.getQuantity() < request.getQuantity()) {
            return ResponseEntity.badRequest().build();
        }

        ProductDistributor transaction = new ProductDistributor();
        transaction.setProduct(product);
        transaction.setDistributor(distributorService.getDistributorById(request.getDistributorId()));
        transaction.setOrderedDate(LocalDateTime.now());
        transaction.setQuantity(request.getQuantity());
        transaction.setStatus("waiting");

        ProductDistributor savedTransaction = productDistributorService.save(transaction);

        return ResponseEntity.ok(supToDisMapper.toSupToDisResponse(savedTransaction));
    }

    @PostMapping("/reject")
    public ResponseEntity<DisToSellResponse> rejectSeller(@RequestBody SupToDisRequest request) {
        if (request.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        DistributorSeller transaction = new DistributorSeller();
        transaction = distributorSellerService.findDistributorSellerById(request.getId());
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        transaction.setStatus("rejected");

        DistributorSeller savedTransaction = distributorSellerService.update(transaction);

        return ResponseEntity.ok(
                disToSellMapper.toResponse(savedTransaction)
        );
    }

    @PostMapping("/approved")
    public ResponseEntity<DisToSellResponse> approvedSeller(@RequestBody SupToDisRequest request) {
        if (request.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        DistributorSeller transaction = new DistributorSeller();
        transaction = distributorSellerService.findDistributorSellerById(request.getId());
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        transaction.setStatus("approved");

        DistributorSeller savedTransaction = distributorSellerService.update(transaction);

        return ResponseEntity.ok(
                disToSellMapper.toResponse(savedTransaction)
        );
    }

    @PostMapping("/send-to-seller")
    public ResponseEntity<DisToSellResponse> sendToSeller(@RequestBody DisToSellRequest request) {
        DistributorSeller transaction = new DistributorSeller();
        if (request.getId() != null) {
            transaction = distributorSellerService.findDistributorSellerById(request.getId());
        }

        DistributorWarehouse distributorWarehouse = distributorWarehouseService
                .getDistributorWarehouseById(request.getWarehouseId());
        Seller seller = sellerService.findSellerById(
                request.getSellerId()
        );
        if (distributorWarehouse == null || seller==null) {
            return ResponseEntity.notFound().build();
        }

        if (distributorWarehouse.getQuantity() < request.getQuantity()) {
            return ResponseEntity.badRequest().build();
        }
        // update quantity for distributor warehouse
        distributorWarehouse.setQuantity(
                distributorWarehouse.getQuantity()-request.getQuantity()
        );


        transaction.setDistributorWarehouse(distributorWarehouse);
        transaction.setSeller(seller);
        transaction.setQuantity(request.getQuantity());
        transaction.setSentDate(LocalDateTime.now());
        transaction.setStatus("sending");

        SellerWarehouse sellerWarehouse = new SellerWarehouse();
        sellerWarehouse.setSeller(seller);
        sellerWarehouse.setQuantity(0L);
        SellerWarehouse savedSellerWarehouse = sellerWareHouseService.save(sellerWarehouse);

        transaction.setSellerWarehouse(savedSellerWarehouse);

        DistributorSeller savedTransaction = distributorSellerService.save(transaction);
        return ResponseEntity.ok(
                disToSellMapper.toResponse(savedTransaction)
        );
    }
}
