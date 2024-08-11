package com.quang.tttn.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long productId;
    private String productName;
    private String productBrand;
    private String productOrigin;
    private String productCertification;
    private String productWeight;
    private String productCommit;
    private String productPlanting;
    private Long quantity;
    private String characteristic;
    private String seed;
    private String cook;
    private String note;
    private String image;
    private LocalDateTime plantingDate;
    private LocalDateTime harvestDate;
    private Long supplierId;
    private SupplierResponse supplier;
}
