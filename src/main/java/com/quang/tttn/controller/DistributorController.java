package com.quang.tttn.controller;

import com.quang.tttn.model.Response.DistributorResponse;
import com.quang.tttn.model.Response.SupToDisResponse;
import com.quang.tttn.model.entity.Distributor;
import com.quang.tttn.model.entity.ProductDistributor;
import com.quang.tttn.model.mapper.DistributorMapper;
import com.quang.tttn.model.mapper.SupToDisMapper;
import com.quang.tttn.model.request.DistributorRequest;
import com.quang.tttn.service.DistributorService;
import com.quang.tttn.service.ProductDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            productDistributor.setStatus("Received");
            productDistributor = productDistributorService.update(productDistributor);
            return ResponseEntity.ok(supToDisMapper.toSupToDisResponse(productDistributor));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
