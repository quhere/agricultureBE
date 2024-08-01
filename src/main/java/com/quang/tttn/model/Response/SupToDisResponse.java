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
public class SupToDisResponse {
    private Long id;
    private Long productId;
    private Long distributorId;
    private Long quantity;
    private LocalDateTime sentDate;
    private LocalDateTime receivedDate;
    private String status;
    private Long warehouseId;
}
