package com.quang.tttn.model.Response;

import lombok.Data;

@Data
public class SellerWarehouseResponse {
    private Long sellerWarehouseId;
    private Long quantity = 0L;
    private ProductResponse product;
    private DistributorResponse distributor;
    private Long distributorWarehouseId;
}
