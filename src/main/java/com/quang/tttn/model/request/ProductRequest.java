package com.quang.tttn.model.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;

    private String characteristic;

    private String seed;

    private String cook;

    private String note;

    private String image;

    private Long supplierId;
}
