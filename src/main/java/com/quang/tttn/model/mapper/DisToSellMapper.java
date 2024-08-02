package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.DisToSellResponse;
import com.quang.tttn.model.entity.DistributorSeller;
import com.quang.tttn.model.entity.ProductDistributor;
import com.quang.tttn.service.ProductDistributorService;
import com.quang.tttn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DisToSellMapper {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDistributorService productDistributorService;
    public DisToSellResponse toResponse(DistributorSeller distributorSeller) {
        ProductDistributor productDistributor = productDistributorService.findByDistributorWarehouseId(
                distributorSeller.getDistributorWarehouse().getWarehouseId()
        );
        System.out.println(productDistributor);
        DisToSellResponse response = new DisToSellResponse();
        response.setId(distributorSeller.getId());
        response.setDistributorWarehouseId(
                distributorSeller.getDistributorWarehouse().getWarehouseId()
        );
        response.setProduct(
                productMapper.toProductResponse(
                        productDistributor.getProduct()
                )
        );
        response.setSellerId(distributorSeller.getSeller().getSellerId());
        response.setQuantity(distributorSeller.getQuantity());
        response.setOrderedDate(distributorSeller.getOrderedDate());
        response.setSentDate(distributorSeller.getSentDate());
        response.setReceivedDate(distributorSeller.getReceivedDate());
        response.setStatus(distributorSeller.getStatus());
        if (distributorSeller.getSellerWarehouse() !=null) {
            response.setSellerWarehouseId(
                    distributorSeller.getSellerWarehouse().getWarehouseId()
            );
        }
        return response;
    }

    public List<DisToSellResponse> toResponseList(List<DistributorSeller> distributorSellerList) {
        List<DisToSellResponse> disToSellResponseList = new ArrayList<>();
        for (DistributorSeller distributorSeller: distributorSellerList) {
            disToSellResponseList.add(toResponse(distributorSeller));
        }
        return disToSellResponseList;
    }

}
