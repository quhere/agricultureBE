package com.quang.tttn.model.Response;

import lombok.Data;

@Data
public class InfoResponse {
    private ProductResponse  product;
    private SupplierResponse supplier;
    private DistributorResponse distributor;
    private SellerResponse seller;
}
