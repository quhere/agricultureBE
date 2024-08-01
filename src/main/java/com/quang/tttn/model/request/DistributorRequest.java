package com.quang.tttn.model.request;

import lombok.Data;

@Data
public class DistributorRequest {
    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String fax;
}
