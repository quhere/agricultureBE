package com.quang.tttn.model.Response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Long transactionId;

    private String productId;

    private String distributorId;

    private String sellerId;

    private Date suppToDist;

    private Date distReceive;

    private Date distToSeller;

    private Date suppToSeller;

    private Date sellerReceive;

}
