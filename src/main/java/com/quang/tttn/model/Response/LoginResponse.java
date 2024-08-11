package com.quang.tttn.model.Response;

import lombok.Data;

@Data
public class LoginResponse {
    private String role;

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String fax;

    private Boolean status = false;
}
