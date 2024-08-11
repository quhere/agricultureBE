package com.quang.tttn.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String role;
    private String email;
    private String password;
}
