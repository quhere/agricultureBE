package com.quang.tttn.controller;

import com.quang.tttn.model.Response.ProductResponse;
import com.quang.tttn.model.entity.Product;
import com.quang.tttn.model.mapper.ProductMapper;
import com.quang.tttn.model.request.ProductRequest;
import com.quang.tttn.service.ProductService;
import com.quang.tttn.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        if (products!=null && !products.isEmpty()) {
            productResponses = productMapper.toProductResponseList(products);
        }

        return productResponses;
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        ProductResponse productResponse = new ProductResponse();
        if (product != null) {
            productResponse = productMapper.toProductResponse(product);
        }
        return productResponse;
    }

    @PostMapping("/create")
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .characteristic(request.getCharacteristic())
                .seed(request.getSeed())
                .cook(request.getCook())
                .note(request.getNote())
                .image(request.getImage())
                .supplier(supplierService.findById(request.getSupplierId()))
                .build();
        return productMapper.toProductResponse(productService.save(product));
    }


}
