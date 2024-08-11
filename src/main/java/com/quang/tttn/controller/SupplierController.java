package com.quang.tttn.controller;

import com.quang.tttn.model.Response.DistributorResponse;
import com.quang.tttn.model.Response.ProductResponse;
import com.quang.tttn.model.Response.SupToDisResponse;
import com.quang.tttn.model.Response.SupplierResponse;
import com.quang.tttn.model.entity.*;
import com.quang.tttn.model.mapper.DistributorMapper;
import com.quang.tttn.model.mapper.ProductMapper;
import com.quang.tttn.model.mapper.SupToDisMapper;
import com.quang.tttn.model.mapper.SupplierMapper;
import com.quang.tttn.model.request.SupToDisRequest;
import com.quang.tttn.model.request.SupplierRequest;
import com.quang.tttn.repository.ProductRepository;
import com.quang.tttn.service.*;
import com.quang.tttn.utils.QRCodeGenerator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private DistributorMapper distributorMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

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
                .status(true)
                .role("supplier")
                .avtUrl("https://quangcaonhat.com/wp-content/uploads/2022/03/z2233894235295_36a2ac275a225d3108708d38b16764a1-768x576.jpg")
                .build();

        System.out.println(supplier);
        return supplierMapper.toResponse(supplierService.save(supplier));
    }

    @SneakyThrows
    @PostMapping("/send-product")
    public ResponseEntity<SupToDisResponse> sendProduct(@RequestBody SupToDisRequest request) {
        ProductDistributor productDistributor = new ProductDistributor();
        if (request.getId() != null ) {
            productDistributor = productDistributorService.findById(request.getId());
        }

        Product product = productService.findById(request.getProductId());
        if (product.getQuantity() < request.getQuantity()) {
            return ResponseEntity.badRequest().build();
        }
        product.setQuantity(product.getQuantity()-request.getQuantity());

        Distributor distributor = distributorService.getDistributorById(request.getDistributorId());
        DistributorWarehouse distributorWarehouse = new DistributorWarehouse();

        if (productDistributor != null) {
//            productDistributor.setProduct(product);
//            productDistributor.setDistributor(distributor);
//            productDistributor.setQuantity(request.getQuantity());

            distributorWarehouse.setQuantity(0L);
            distributorWarehouse.setDistributor(distributor);

            DistributorWarehouse savedWarehouse = distributorWarehouseService.save(distributorWarehouse);

            productDistributor.setSentDate(LocalDateTime.now());
            productDistributor.setStatus("sending");
            productDistributor.setDistributorWarehouse(savedWarehouse);

            ProductDistributor transaction = productDistributorService.save(productDistributor);
            QRCodeGenerator.supplierSend(transaction);

            return ResponseEntity.ok(supToDisMapper.toSupToDisResponse(transaction));
        }
        else {
            // create distributor warehouse
            distributorWarehouse.setQuantity(0L);
            distributorWarehouse.setDistributor(distributor);

            DistributorWarehouse savedWarehouse = distributorWarehouseService.save(distributorWarehouse);

            // create transaction
            ProductDistributor productDistributor1 = new ProductDistributor();
            productDistributor1.setProduct(product);
            productDistributor1.setDistributor(distributor);
            productDistributor1.setQuantity(request.getQuantity());
            productDistributor1.setSentDate(LocalDateTime.now());
            productDistributor1.setStatus("sending");
            productDistributor1.setDistributorWarehouse(savedWarehouse);

            ProductDistributor transaction = productDistributorService.save(productDistributor1);
            QRCodeGenerator.supplierSend(transaction);

            return ResponseEntity.ok(supToDisMapper.toSupToDisResponse(transaction));
        }

    }

    @PostMapping("/reject")
    private ResponseEntity<SupToDisResponse> reject(@RequestBody SupToDisRequest request) {
        ProductDistributor productDistributor = productDistributorService.findById(request.getId());
        if (productDistributor == null) {
            return ResponseEntity.badRequest().build();
        }
        productDistributor.setStatus("rejected");
        ProductDistributor saveProductDistributor = productDistributorService.update(productDistributor);
        return ResponseEntity.ok(
                supToDisMapper.toSupToDisResponse(saveProductDistributor)
        );
    }

    @PostMapping("/approve")
    private ResponseEntity<SupToDisResponse> approve(@RequestBody SupToDisRequest request) {
        ProductDistributor productDistributor = productDistributorService.findById(request.getId());
        if (productDistributor == null) {
            return ResponseEntity.badRequest().build();
        }
        productDistributor.setStatus("approved");
        ProductDistributor saveProductDistributor = productDistributorService.update(productDistributor);
        return ResponseEntity.ok(
                supToDisMapper.toSupToDisResponse(saveProductDistributor)
        );
    }

    @GetMapping("/distributors")
    public ResponseEntity<List<DistributorResponse>> distributors() {
        List<Distributor> list = distributorService.findAllDistributorInProductDistributors();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<DistributorResponse> responseList = new ArrayList<>();
        responseList = distributorMapper.toDistributorResponses(list);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/transactions/{supplierId}")
    public ResponseEntity<List<SupToDisResponse>> transactions(
            @PathVariable Long supplierId
    ) {
        if (supplierId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ProductDistributor> list = productDistributorService
                .findBySupplierId(supplierId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SupToDisResponse> responseList = supToDisMapper
                .toSupToDisResponseList(list);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/products/{supplierId}")
    public ResponseEntity<List<ProductResponse>> getProductsBySupplierId(@PathVariable Long supplierId) {
        if (supplierId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Product> list = productRepository.findAllBySupplier_SupplierId(supplierId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ProductResponse> responseList = productMapper.toProductResponseList(list);
        return ResponseEntity.ok(responseList);
    }
}
