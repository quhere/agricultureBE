package com.quang.tttn.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long productId;

    private String productName;

    private String characteristic;

    private String seed;

    private String cook;

    private String note;

    private String image;

    private Long supplierId;
}
