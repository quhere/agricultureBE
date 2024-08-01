package com.quang.tttn.controller;

import com.google.zxing.WriterException;
import com.quang.tttn.model.Response.SupToDisResponse;
import com.quang.tttn.model.Response.SupplierResponse;
import com.quang.tttn.model.entity.*;
import com.quang.tttn.model.mapper.SupToDisMapper;
import com.quang.tttn.model.mapper.SupplierMapper;
import com.quang.tttn.model.request.SupToDisRequest;
import com.quang.tttn.model.request.SupplierRequest;
import com.quang.tttn.service.*;
import com.quang.tttn.utils.QRCodeGenerator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private DistributorService distributorService;
    @Autowired
    private ProductDistributorService productDistributorService;
    @Autowired
    private SupToDisMapper supToDisMapper;
    @Autowired
    private DistributorWarehouseService distributorWarehouseService;
    @GetMapping
    public List<SupplierResponse> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.findAll();
        List<SupplierResponse> supplierResponses = new ArrayList<>();
        if (suppliers != null && !suppliers.isEmpty()) {
            supplierResponses = supplierMapper.toResponseList(suppliers);
        }
        return supplierResponses;
    }

    @GetMapping("/{supplierId}")
    public SupplierResponse getSupplierById(@PathVariable Long supplierId) {
        Supplier supplier = new Supplier();
        SupplierResponse supplierResponse = new SupplierResponse();
        if (supplierId != null) {
            supplier = supplierService.findById(supplierId);
            if (supplier != null) {
                supplierResponse = supplierMapper.toResponse(supplier);
            }
        }

        return supplierResponse;
    }

    @PostMapping("/create")
    public SupplierResponse createSupplier(@RequestBody SupplierRequest request) {
        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .fax(request.getFax())
                .build();

//        System.out.println(supplier);
        return supplierMapper.toResponse(supplierService.save(supplier));
    }

    @SneakyThrows
    @PostMapping("/send-product")
    public SupToDisResponse sendProduct(@RequestBody SupToDisRequest request) {
        ProductDistributor productDistributor = productDistributorService
                .findByProductIdAndDistributorIdAndStatusWaiting(
                        request.getProductId(),
                        request.getDistributorId()
                );

        Product product = productService.findById(request.getProductId());
        Distributor distributor = distributorService.getDistributorById(request.getDistributorId());
        DistributorWarehouse distributorWarehouse = new DistributorWarehouse();
        if (productDistributor != null) {
            productDistributor.setProduct(product);
            productDistributor.setDistributor(distributor);
            productDistributor.setQuantity(request.getQuantity());
            productDistributor.setSentDate(LocalDateTime.now());
            productDistributor.setStatus("Sending");

            ProductDistributor transaction = productDistributorService.save(productDistributor);
            QRCodeGenerator.generateQRCode(transaction);

            return supToDisMapper.toSupToDisResponse(transaction);
        }
        else {
            // create distributor warehouse
            distributorWarehouse.setQuantity(request.getQuantity());
            distributorWarehouse.setDistributor(distributor);

            DistributorWarehouse savedWarehouse = distributorWarehouseService.save(distributorWarehouse);

            // create transaction
            ProductDistributor productDistributor1 = new ProductDistributor();
            productDistributor1.setProduct(product);
            productDistributor1.setDistributor(distributor);
            productDistributor1.setQuantity(request.getQuantity());
            productDistributor1.setSentDate(LocalDateTime.now());
            productDistributor1.setStatus("Sending");
            productDistributor1.setDistributorWarehouse(savedWarehouse);

            ProductDistributor transaction = productDistributorService.save(productDistributor1);
            QRCodeGenerator.generateQRCode(transaction);

            return supToDisMapper.toSupToDisResponse(transaction);
        }

    }
}
