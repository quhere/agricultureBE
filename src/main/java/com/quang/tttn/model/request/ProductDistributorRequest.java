package com.quang.tttn.model.request;

import com.quang.tttn.model.entity.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class ProductDistributorRequest {
    private Long productId;
    private Long distributorId;
    private Long quantity;
}
