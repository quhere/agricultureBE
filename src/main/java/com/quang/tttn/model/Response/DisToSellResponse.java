package com.quang.tttn.model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisToSellResponse {
    private Long id;
    private Long distributorWarehouseId;
    private ProductResponse product;
    private Long sellerId;
    private Long quantity;
    private LocalDateTime orderedDate;
    private LocalDateTime sentDate;
    private LocalDateTime receivedDate;
    private String status;
    private Long sellerWarehouseId;
}
