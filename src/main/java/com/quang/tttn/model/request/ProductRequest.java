package com.quang.tttn.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class ProductRequest {
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
    private String plantingDate;
    private String harvestDate;
    private Long supplierId;
    private MultipartFile productImage;
}
