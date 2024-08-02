package com.quang.tttn.model.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DisToSellRequest {
    private Long id;
    private Long warehouseId;
    private Long sellerId;
    private Long quantity;
    private LocalDateTime orderedDate;
    private LocalDateTime sentDate;
    private LocalDateTime receivedDate;
}
