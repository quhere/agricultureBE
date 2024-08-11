package com.quang.tttn.controller;

import com.google.zxing.WriterException;
import com.quang.tttn.model.Response.*;
import com.quang.tttn.model.entity.*;
import com.quang.tttn.model.mapper.*;
import com.quang.tttn.model.request.DisToSellRequest;
import com.quang.tttn.model.request.DistributorRequest;
import com.quang.tttn.model.request.SupToDisRequest;
import com.quang.tttn.repository.DistributorRepository;
import com.quang.tttn.repository.DistributorSellerRepository;
import com.quang.tttn.repository.DistributorWarehouseRepository;
import com.quang.tttn.repository.ProductDistributorRepository;
import com.quang.tttn.service.*;
import com.quang.tttn.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private ProductDistributorRepository productDistributorRepository;
    @Autowired
    private SupToDisMapper supToDisMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private DistributorWarehouseService distributorWarehouseService;
    @Autowired
    private DistributorSellerService distributorSellerService;
    @Autowired
    private DistributorSellerRepository distributorSellerRepository;
    @Autowired
    private DisToSellMapper disToSellMapper;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerWareHouseService sellerWareHouseService;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private DistributorWarehouseRepository distributorWarehouseRepository;
    @Autowired
    private DisWarehouseResponse2Mapper disWarehouseResponse2Mapper;

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
        distributor.setStatus(true);
        distributor.setRole("distributor");
        distributor.setAvtUrl("https://kamereo.vn/blog/wp-content/uploads/2024/05/dai-ly-gao-tphcm-1.jpg");

        return ResponseEntity.ok(
                distributorMapper.toDistributorResponse(
                        distributorService.saveDistributor(distributor)
                )
        );
    }

    @PostMapping("/received")
    public ResponseEntity<SupToDisResponse> receivedDistributor(
            @RequestBody SupToDisRequest request
    ) {
        ProductDistributor productDistributor = productDistributorService.findById(request.getId());

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
        transaction.setStatus("pending");

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
    public ResponseEntity<DisToSellResponse> sendToSeller(@RequestBody DisToSellRequest request) throws IOException, WriterException {
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

        QRCodeGenerator.distributorSend(savedTransaction);
        return ResponseEntity.ok(
                disToSellMapper.toResponse(savedTransaction)
        );
    }

    @GetMapping("/supplier/{transactionId}")
    public ResponseEntity<SupToDisResponse> supplierSeller(@PathVariable Long transactionId) {
        ProductDistributor transaction = productDistributorService.findById(transactionId);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                supToDisMapper.toSupToDisResponse(transaction)
        );
    }

    @GetMapping("/sellers/{distributorId}")
    public ResponseEntity<List<SellerResponse>> getSellers(
            @PathVariable Long distributorId
    )
    {
        List<Seller> list = distributorSellerRepository.findSellersByDistributorId(distributorId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SellerResponse> responses = sellerMapper.toSellerResponses(list);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/seller-transactions/{distributorId}")
    public ResponseEntity<List<DisToSellResponse>> getSellerTransactions(
            @PathVariable Long distributorId
    )
    {
        List<DistributorSeller> list = distributorSellerRepository.findAllByDistributorId(distributorId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<DisToSellResponse> listResponse = disToSellMapper.toResponseList(list);
        return ResponseEntity.ok(listResponse);
    }

    @GetMapping("/supplier-transactionId/{distributorId}")
    public ResponseEntity<List<SupToDisResponse>> getSupplierTransactions(
            @PathVariable Long distributorId
    )
    {
        List<ProductDistributor> list = productDistributorRepository.findAllByDistributorId(distributorId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SupToDisResponse> responses = supToDisMapper.toSupToDisResponseList(list);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/suppliers/{distributorId}")
    public ResponseEntity<List<SupplierResponse>> getSuppliers(
            @PathVariable Long distributorId
    )
    {
        List<Supplier> list = productDistributorRepository.findSuppliersByDistributorId(distributorId);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SupplierResponse> responses = supplierMapper.toResponseList(list);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-warehouses/{distributorId}")
    public ResponseEntity<List<DisWarehouseResponse2>> getWarehousesByDistributorId(
            @PathVariable Long distributorId
    )
    {
        List<DistributorWarehouse> list = distributorWarehouseRepository
                .findAllByDistributor_DistributorId(distributorId);

        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<DisWarehouseResponse2> listResponse = disWarehouseResponse2Mapper.toResponseList(list);
        return ResponseEntity.ok(listResponse);
    }
}
