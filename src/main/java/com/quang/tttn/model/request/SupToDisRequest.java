package com.quang.tttn.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupToDisRequest {
    private Long id;
    private Long productId;
    private Long distributorId;
    private Long quantity;
    private LocalDateTime sentDate;
    private LocalDateTime receivedDate;
}
