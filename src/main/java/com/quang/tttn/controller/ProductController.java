package com.quang.tttn.controller;

import com.google.zxing.WriterException;
import com.quang.tttn.model.Response.ProductResponse;
import com.quang.tttn.model.entity.Product;
import com.quang.tttn.model.mapper.ProductMapper;
import com.quang.tttn.model.request.ProductRequest;
import com.quang.tttn.service.ProductService;
import com.quang.tttn.service.SupplierService;
import com.quang.tttn.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ProductResponse createProduct(@RequestBody ProductRequest request) throws IOException, WriterException {

        LocalDate plantingDay = (request.getPlantingDate()==null || request.getPlantingDate().isEmpty()) ?
                LocalDate.now() :
                LocalDate.parse(request.getPlantingDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime plantingDate = plantingDay.atStartOfDay();
        LocalDate harvestDay = (request.getHarvestDate()==null || request.getHarvestDate().isEmpty()) ?
                LocalDate.now() :
                LocalDate.parse(request.getHarvestDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime harvestDate = harvestDay.atStartOfDay();
        String brand = request.getProductBrand()!=null?request.getProductBrand():"brand";
        String origin = request.getProductOrigin()!=null?request.getProductOrigin():"origin";
        String certification = request.getProductCertification()!=null?request.getProductCertification():"certification";
        String weight = request.getProductWeight()!=null?request.getProductWeight():"weight";
        String commit = request.getProductCommit()!=null?request.getProductCommit():"commit";
        String planting = request.getProductPlanting()!=null?request.getProductPlanting():"planting";
        String image = "";
        if (request.getImage() == null || request.getImage().isEmpty()) {
            image = "https://cdn.tgdd.vn/Products/Images/2513/bhx/gao-cac-loai-202209301453236694.png";
        }
        else image = request.getImage();

        Product product = Product.builder()
                .productName(request.getProductName())
                .productBrand(brand)
                .productOrigin(origin)
                .productCertification(certification)
                .productWeight(weight)
                .productCommit(commit)
                .productPlanting(planting)
                .characteristic(request.getCharacteristic())
                .seed(request.getSeed())
                .cook(request.getCook())
                .note(request.getNote())
                .quantity(request.getQuantity())
                .image(image)
                .plantingDate(plantingDate)
                .harvestDate(harvestDate)
                .supplier(supplierService.findById(request.getSupplierId()))
                .build();
        Product savedProduct = productService.save(product);
        System.out.println(savedProduct);
        QRCodeGenerator.productInfo(savedProduct);
        return productMapper.toProductResponse(savedProduct);
    }


}
