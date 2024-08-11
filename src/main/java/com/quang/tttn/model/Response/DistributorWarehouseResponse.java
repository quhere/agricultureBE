package com.quang.tttn.model.Response;

import lombok.Data;

@Data
public class DistributorWarehouseResponse {
    private Long warehouseId;
    private Long quantity;
    private DistributorResponse distributor;
    private SupToDisResponse supToDis;
}
