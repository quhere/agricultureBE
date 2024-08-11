package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.SellerResponse;
import com.quang.tttn.model.Response.SellerWarehouseResponse;
import com.quang.tttn.model.entity.SellerWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SellerWarehouseMapper {
    @Autowired
    private DistributorMapper distributorMapper;
    @Autowired
    private ProductMapper productMapper;
    public SellerWarehouseResponse toResponse(SellerWarehouse sellerWarehouse) {
        SellerWarehouseResponse response = new SellerWarehouseResponse();
        if (sellerWarehouse != null) {
            response.setSellerWarehouseId(sellerWarehouse.getWarehouseId());
            response.setQuantity(sellerWarehouse.getQuantity());
            if (sellerWarehouse.getDistributorSeller() != null) {
                response.setDistributor(
                        distributorMapper.toDistributorResponse(
                                sellerWarehouse.getDistributorSeller()
                                        .getDistributorWarehouse()
                                        .getDistributor()
                        )
                );

                response.setProduct(
                        productMapper.toProductResponse(
                                sellerWarehouse.getDistributorSeller()
                                        .getDistributorWarehouse()
                                        .getProductDistributor()
                                        .getProduct()
                        )
                );

                response.setDistributorWarehouseId(
                        sellerWarehouse.getDistributorSeller()
                                .getDistributorWarehouse()
                                .getWarehouseId()
                );
            }
        }
        return response;
    }

    public List<SellerWarehouseResponse> toResponseList(List<SellerWarehouse> sellerWarehouses) {
        List<SellerWarehouseResponse> responseList = new ArrayList<>();
        for (SellerWarehouse sellerWarehouse : sellerWarehouses) {
            responseList.add(toResponse(sellerWarehouse));
        }
        return responseList;
    }
}
