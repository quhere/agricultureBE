package com.quang.tttn.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SupplierRequest {
    private Long productId;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String fax;

    private MultipartFile avt;
}
