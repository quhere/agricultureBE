package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.ProductResponse;
import com.quang.tttn.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    private final SupplierMapper supplierMapper;

    public ProductMapper(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    public ProductResponse toProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setProductBrand(product.getProductBrand());
        productResponse.setProductOrigin(product.getProductOrigin());
        productResponse.setProductCertification(product.getProductCertification());
        productResponse.setProductWeight(product.getProductWeight());
        productResponse.setProductCommit(product.getProductCommit());
        productResponse.setProductPlanting(product.getProductPlanting());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setCharacteristic(product.getCharacteristic());
        productResponse.setSeed(product.getSeed());
        productResponse.setCook(product.getCook());
        productResponse.setNote(product.getNote());
        productResponse.setImage(product.getImage());
        productResponse.setPlantingDate(product.getPlantingDate());
        productResponse.setHarvestDate(product.getHarvestDate());
        productResponse.setSupplierId(product.getSupplier().getSupplierId());
        productResponse.setSupplier(
                supplierMapper.toResponse(
                        product.getSupplier()
                )
        );
        return productResponse;
    }

    public List<ProductResponse> toProductResponseList(List<Product> productList) {
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product product : productList) {
            productResponseList.add(toProductResponse(product));

        }
        return productResponseList;
    }
}
