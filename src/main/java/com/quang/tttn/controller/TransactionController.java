package com.quang.tttn.controller;

import com.quang.tttn.model.Response.InfoResponse;
import com.quang.tttn.model.Response.ProductResponse;
import com.quang.tttn.model.entity.DistributorSeller;
import com.quang.tttn.model.entity.ProductDistributor;
import com.quang.tttn.model.entity.Seller;
import com.quang.tttn.model.entity.SellerWarehouse;
import com.quang.tttn.model.mapper.DistributorMapper;
import com.quang.tttn.model.mapper.ProductMapper;
import com.quang.tttn.model.mapper.SellerMapper;
import com.quang.tttn.model.mapper.SupplierMapper;
import com.quang.tttn.repository.DistributorSellerRepository;
import com.quang.tttn.service.DistributorSellerService;
import com.quang.tttn.service.ProductDistributorService;
import com.quang.tttn.service.SellerWareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    @Autowired
    private SellerWareHouseService sellerWareHouseService;
    @Autowired
    private DistributorSellerService distributorSellerService;
    @Autowired
    private DistributorSellerRepository distributorSellerRepository;
    @Autowired
    private ProductDistributorService productDistributorService;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private DistributorMapper distributorMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SupplierMapper supplierMapper;

    @GetMapping("/{sellerWarehouseId}")
    public ResponseEntity<InfoResponse> detailProduct(@PathVariable Long sellerWarehouseId) {
        InfoResponse response = new InfoResponse();

        SellerWarehouse sellerWarehouse = sellerWareHouseService.getSellerWarehouseById(sellerWarehouseId);
        if (sellerWarehouse == null) {
            System.out.println("sellerWarehouse is null");
            return ResponseEntity.notFound().build();
        }
        Seller seller = sellerWarehouse.getSeller();
        response.setSeller(sellerMapper.toSellerResponse(seller));

        DistributorSeller distributorSeller = distributorSellerRepository
                .findBySellerWarehouse_WarehouseId(
                        sellerWarehouseId
                );
        if (distributorSeller == null) {
            System.out.println("distributorSeller is null");
            return ResponseEntity.notFound().build();
        }
        response.setDistributor(distributorMapper.toDistributorResponse(distributorSeller.getDistributorWarehouse().getDistributor()));

        ProductDistributor productDistributor = productDistributorService.findByDistributorWarehouseId(
                distributorSeller.getDistributorWarehouse().getWarehouseId()
        );
        if (productDistributor != null) {
            if (productDistributor.getProduct() != null) {
                response.setProduct(productMapper.toProductResponse(productDistributor.getProduct()));
                response.setSupplier(supplierMapper.toResponse(productDistributor.getProduct().getSupplier()));
            }
        }

        return ResponseEntity.ok(response);
    }
}
