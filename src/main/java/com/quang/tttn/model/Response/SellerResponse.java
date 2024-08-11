package com.quang.tttn.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerResponse {
    private Long sellerId;
    private String role;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String fax;
    private Boolean status;
    private String avtUrl;
    private String taxCode;
    private String establishment;
    private String manager;
    private String activated;
    private String description;
}
