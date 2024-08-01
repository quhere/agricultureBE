package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.SupToDisResponse;
import com.quang.tttn.model.entity.ProductDistributor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupToDisMapper {
    public SupToDisResponse toSupToDisResponse(ProductDistributor transaction) {
        SupToDisResponse response = new SupToDisResponse();
        response.setId(transaction.getId());
        response.setProductId(transaction.getProduct().getProductId());
        response.setDistributorId(transaction.getDistributor().getDistributorId());
        response.setQuantity(transaction.getQuantity());
        response.setSentDate(transaction.getSentDate());
        response.setReceivedDate(transaction.getReceivedDate());
        response.setStatus(transaction.getStatus());
        response.setWarehouseId(transaction.getDistributorWarehouse().getWarehouseId());
        return response;
    }
    public List<SupToDisResponse> toSupToDisResponseList(
            List<ProductDistributor> transactions
    ) {
        List<SupToDisResponse> responses = new ArrayList<>();
        for (ProductDistributor transaction : transactions) {
            responses.add(toSupToDisResponse(transaction));
        }
        return responses;
    }
}
