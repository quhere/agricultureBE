package com.quang.tttn.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributorResponse {
    private Long distributorId;
    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String fax;

    private Boolean status = false;
}
