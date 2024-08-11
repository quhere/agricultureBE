package com.quang.tttn.controller;

import com.google.zxing.WriterException;
import com.quang.tttn.model.Response.*;
import com.quang.tttn.model.entity.*;
import com.quang.tttn.model.mapper.*;
import com.quang.tttn.model.request.DisToSellRequest;
import com.quang.tttn.model.request.SellerRequest;
import com.quang.tttn.repository.DistributorSellerRepository;
import com.quang.tttn.repository.DistributorWarehouseRepository;
import com.quang.tttn.repository.SellerWarehouseRepository;
import com.quang.tttn.service.DistributorSellerService;
import com.quang.tttn.service.DistributorWarehouseService;
import com.quang.tttn.service.SellerService;
import com.quang.tttn.service.SellerWareHouseService;
import com.quang.tttn.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @Autowired
    private DistributorSellerRepository distributorSellerRepository;
    @Autowired
    private DistributorMapper distributorMapper;
    @Autowired
    private DistributorWarehouseMapper distributorWarehouseMapper;
    @Autowired
    private DisWarehouseResponse2Mapper disWarehouseResponse2Mapper;
    @Autowired
    private DistributorWarehouseRepository distributorWarehouseRepository;
    @Autowired
    private SellerWarehouseRepository sellerWarehouseRepository;
    @Autowired
    private SellerWarehouseMapper sellerWarehouseMapper;

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
        seller.setRole("seller");
        seller.setAvtUrl("https://gaosachsonghau.com/kcfinder/upload/images/mo-dai-ly-gao.jpg");

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

        if (request.getWarehouseId() == null) {
            System.out.println("WarehouseId null");
        }
        if (request.getSellerId() == null) {
            System.out.println("seller null");
        }

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
        distributorSeller.setStatus("pending");

        DistributorSeller savedTransaction = distributorSellerService.save(distributorSeller);

        return ResponseEntity.ok(
                disToSellMapper.toResponse(savedTransaction)
        );
    }

    @PostMapping("/received/{transactionId}")
    public ResponseEntity<DisToSellResponse> receive(@PathVariable Long transactionId) throws IOException, WriterException {
        DistributorSeller distributorSeller = distributorSellerService.findDistributorSellerById(transactionId);
        if (distributorSeller == null) {
            return ResponseEntity.notFound().build();
        }
        distributorSeller.setReceivedDate(LocalDateTime.now());
        distributorSeller.setStatus("received");
        DistributorSeller savedTransaction = distributorSellerService.save(distributorSeller);

        SellerWarehouse sellerWarehouse = distributorSeller.getSellerWarehouse();
        sellerWarehouse.setQuantity(distributorSeller.getQuantity());
        SellerWarehouse savedWarehouse = sellerWareHouseService.save(sellerWarehouse);

        QRCodeGenerator.sellerReceived(savedWarehouse);
        return ResponseEntity.ok(
                disToSellMapper.toResponse(savedTransaction)
        );
    }

    @GetMapping("/distributor-transactions/{sellerId}")
    public ResponseEntity<List<DisToSellResponse>> getDistributorTransactions(
            @PathVariable Long sellerId
    )
    {
        List<DistributorSeller> list = distributorSellerRepository.findAllBySellerId(sellerId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<DisToSellResponse> responses = disToSellMapper.toResponseList(list);
        return ResponseEntity.ok(responses);

    }

    @GetMapping("/distributors/{sellerId}")
    public ResponseEntity<List<DistributorResponse>> getDistributors(
            @PathVariable Long sellerId
    )
    {
        List<Distributor> list = distributorSellerRepository.findDistributorsBySellerId(sellerId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<DistributorResponse> responses = distributorMapper.toDistributorResponses(list);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<DisToSellResponse> getTransaction(
            @PathVariable Long transactionId
    )
    {
        DistributorSeller transaction = distributorSellerService.findDistributorSellerById(transactionId);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        DisToSellResponse disToSellResponse = disToSellMapper.toResponse(transaction);
        return ResponseEntity.ok(disToSellResponse);
    }

    @GetMapping("/get-distributor-warehourses")
    public ResponseEntity<List<DistributorWarehouseResponse>> getDistributorWarehouses() {
        List<DistributorWarehouse> list = distributorWarehouseService.getAllDistributorWarehouse();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<DistributorWarehouseResponse> list1 = distributorWarehouseMapper.toResponseList(list);
        return ResponseEntity.ok(list1);
    }

    @GetMapping("/get-all-distributor-warehouses")
    public ResponseEntity<List<DisWarehouseResponse2>> getAllDistributorWarehouses()
    {
        List<DistributorWarehouse> list = distributorWarehouseRepository.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<DisWarehouseResponse2> list1 = disWarehouseResponse2Mapper.toResponseList(list);
        return ResponseEntity.ok(list1);

    }

    @GetMapping("/get-seller-warehouses/{sellerId}")
    public ResponseEntity<List<SellerWarehouseResponse>> getSellerWarehouses(
            @PathVariable Long sellerId
    )
    {
        List<SellerWarehouse> list = sellerWarehouseRepository.findAllBySeller_SellerId(sellerId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<SellerWarehouseResponse> responses = sellerWarehouseMapper.toResponseList(list);
        return ResponseEntity.ok(responses);
    }

}
