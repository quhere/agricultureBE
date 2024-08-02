package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.ProductResponse;
import com.quang.tttn.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .characteristic(product.getCharacteristic())
                .seed(product.getSeed())
                .cook(product.getCook())
                .note(product.getNote())
                .image(product.getImage())
                .plantingDate(product.getPlantingDate())
                .harvestDate(product.getHarvestDate())
                .supplierId(product.getSupplier().getSupplierId())
                .build();
    }

    public List<ProductResponse> toProductResponseList(List<Product> productList) {
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product product : productList) {
            productResponseList.add(toProductResponse(product));

        }
        return productResponseList;
    }
}
