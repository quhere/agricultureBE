package com.quang.tttn.model.Response;

import lombok.Data;

@Data
public class DisWarehouseResponse2 {
    private Long distributorWarehouseId;
    private Long quantity;
    private DistributorResponse distributor;
    private ProductResponse product;
    private SupplierResponse supplier;
//    private
}
